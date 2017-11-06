package com.app.core.utility.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

import com.app.core.annotation.Excel;
import com.app.core.annotation.ExcelTarget;
import com.app.core.model.DateConstant;
import com.app.core.model.Json;
import com.app.core.utility.system.ContextHolderUtils;
import com.app.core.utility.system.DateUtil;

/**
 * Excel 导入工具
 * @author hqb qb.huang@wescxx.com
 * @date 2015年10月13日 上午11:55:26 
 * @version V1.0
 */
public final class ExcelImportUtil {

	/**
	 * Excel 导入 字段类型 Integer,Long,Double,Date,String,Boolean,BigDecimal
	 * @param file
	 * @param pojoClass
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static ImportResult importExcel(File file, Class<?> pojoClass,
			ImportParams params) {
		FileInputStream in = null;
		ImportResult result = null;
		try {
			in = new FileInputStream(file);
			result = importExcelByIs(in, pojoClass, params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * Excel 导入 field 字段类型 Integer,Long,Double,Date,String,Boolean,BigDecimal
	 * @param inputstream
	 * @param pojoClass
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static  ImportResult importExcelByIs(InputStream inputstream,
			Class<?> pojoClass, ImportParams params) throws Exception {
		
		ImportResult iResult = new ImportResult();
		
		Workbook book = null;
		boolean isXSSFWorkbook = true;
		//测试输入流是否支持mark和reset方法
		if (!(inputstream.markSupported())) {
			inputstream = new PushbackInputStream(inputstream, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(inputstream)) {//2003
			book =  new HSSFWorkbook(inputstream);
			isXSSFWorkbook = false;
		}else if (POIXMLDocument.hasOOXMLHeader(inputstream)) {//2007
			book =  new XSSFWorkbook(OPCPackage.open(inputstream));
		}
		Map<String,PictureData> pictures;
		for (int i = 0; i < params.getSheetNum(); i++) {//循环每一个sheet
			if(isXSSFWorkbook){//2007
				pictures = ExcelPublicUtil.getSheetPictrues07(
						(XSSFSheet)book.getSheetAt(i), (XSSFWorkbook)book);
			}else{//2003
				pictures = ExcelPublicUtil.getSheetPictrues03(
						(HSSFSheet)book.getSheetAt(i), (HSSFWorkbook)book);
			}
			
			ImportResult importResult = importExcel(book.getSheetAt(i), pojoClass, params,pictures);
			
			iResult.getErrMess().addAll(importResult.getErrMess());
			
			iResult.getSuccResult().addAll(importResult.getSuccResult());
			
			iResult.getErrResult().addAll(importResult.getErrResult());
		}
		if(params.isNeedSave()){
			saveThisExcel(params,pojoClass,isXSSFWorkbook,book);
		}
		return iResult;
	}
	
	private static void saveThisExcel(ImportParams params,Class<?> pojoClass,
			boolean isXSSFWorkbook, Workbook book) throws Exception {
		String path = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("\\")+getSaveExcelUrl(params,pojoClass);
		path = path.replace("WEB-INF/classes/","");
		path = path.replace("file:/","");
		File savefile = new File(path);
		if(!savefile.exists()){
			savefile.mkdirs();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyMMddHHmmss");
		FileOutputStream fos = new FileOutputStream(path+"/"+format.format(new Date())+"_"
				+Math.round(Math.random()*100000)+(isXSSFWorkbook == true?".xlsx":".xls"));
		book.write(fos);
		fos.close();
	}
	
	
	/**
	 * 获取保存的Excel 的真实路径
	 * @param excelImportEntity
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private static String getSaveExcelUrl(ImportParams params,
			Class<?> pojoClass) throws Exception {
		String url = "";
		if(params.getSaveUrl().equals("upload/excelUpload")){
			url =  pojoClass.getName().split("\\.")[pojoClass.getName().split("\\.").length-1];
			return params.getSaveUrl()+"/"+url.substring(0, url.lastIndexOf("Entity"));
		}
		return params.getSaveUrl();
	}
	
	@SuppressWarnings({"unchecked" })
	private static ImportResult importExcel(Sheet sheet,
			Class<?> pojoClass, ImportParams params, Map<String, PictureData> pictures)  throws Exception {
		
		ImportResult result = new ImportResult();
		
		Map<String, ExcelImportEntity> excelParams = new HashMap<String, ExcelImportEntity>();
		List<ExcelCollectionParams> excelCollection = new ArrayList<ExcelCollectionParams>();
		Field fileds[] = ExcelPublicUtil.getClassFields(pojoClass);
		ExcelTarget etarget = pojoClass.getAnnotation(ExcelTarget.class);
		String targetId = null;
		if (etarget != null) {
			targetId = etarget.id();
		}
		getAllExcelField(targetId, fileds, excelParams, excelCollection,
				pojoClass, null);
		Iterator<Row> rows = sheet.rowIterator();
		for (int j = 0; j < params.getTitleRows(); j++) {
			rows.next();
		}
		Row row = null;
		Iterator<Cell> cellTitle;
		Map<Integer, String> titlemap = new HashMap<Integer, String>();
		for (int j = 0; j < params.getSecondTitleRows(); j++) {
			row = rows.next();
			cellTitle = row.cellIterator();
			int i = row.getFirstCellNum();
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				if(!StringUtils.isEmpty(value)){
					titlemap.put(i, value);
				}
				i = i + 1;
			}
		}
		Object object = null;
		String picId;
		
		while (rows.hasNext()) {
			row = rows.next();
			// 判断是集合元素还是不是集合元素,如果是就继续加入这个集合,不是就创建新的对象
			if ((row.getCell(params.getKeyIndex())==null||
					StringUtils.isEmpty(getKeyValue(row.getCell(params.getKeyIndex()))))
					&& object != null) {
				for (ExcelCollectionParams param : excelCollection) {
					addListContinue(object, param, row,titlemap, targetId,pictures,params);
				}
			} else {
				object = ExcelPublicUtil.createObject(pojoClass, targetId);
				int count = 0;
				
				for(int i = row.getFirstCellNum() ;i<row.getLastCellNum();i++){
					Cell cell = row.getCell(i);
					if(cell == null || Cell.CELL_TYPE_BLANK == cell.getCellType()){//单元格值为空，则跳过，不处理
						++count;
						continue;
					}
					String titleString = (String) titlemap.get(i);
					if (excelParams.containsKey(titleString)) {
						if(excelParams.get(titleString).getType()==2){
							picId = row.getRowNum()+"_"+i;
							saveImage(object,picId,excelParams,
									titleString,pictures,params);
						}else{
							judgeTypeAndSetValue(object, cell, excelParams, titleString);
						}
					}
				}
				
				if(count == row.getLastCellNum() - row.getFirstCellNum()){
					break;
				}
				for (ExcelCollectionParams param : excelCollection) {
					addListContinue(object, param, row,titlemap, targetId,pictures,
							params);
				}
				//完成xls到entity的解析过程
				//对object进行校验
				//初始化校验器
				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				//hibernate 的校验器
				Validator validator = factory.getValidator();
				//校验，校验规则是配置在object对象里面
				Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
				if(constraintViolations.size() >0){//校验失败时，提示相应信息
					for (ConstraintViolation<Object> vl : constraintViolations) {
						String description = "第" +row.getRowNum()+"行，校验出错，"+ vl.getPropertyPath() + "=" + vl.getInvalidValue() + "，" + vl.getMessage();			
						result.getErrMess().add(description);
					}	
					result.getErrResult().add(object);
				}else{//校验成功的object
					result.getSuccResult().add(object);
				}
				object = null;
			}
		}
		return result;
	}
	/**
	 * 获取key的值,针对不同类型获取不同的值
	 *@Author JueYue
	 *@date   2013-11-21
	 *@param cell
	 *@return
	 */
	private static String getKeyValue(Cell cell) {
		Object obj = null;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:obj = cell.getStringCellValue();break;
			case Cell.CELL_TYPE_BOOLEAN:obj = cell.getBooleanCellValue();break;
			case Cell.CELL_TYPE_NUMERIC:obj = cell.getNumericCellValue();break;
		}
		return obj==null?null:obj.toString();
	}
	/**
	 * 
	 * @param object
	 * @param cell
	 * @param excelParams
	 * @param titleString
	 * @param pictures
	 * @param currentImageIndex
	 * @param params
	 * @throws Exception 
	 */
	private static void saveImage(Object object, String picId,
			Map<String, ExcelImportEntity> excelParams, String titleString,
			Map<String, PictureData> pictures, ImportParams params) throws Exception {
		if (pictures == null) {
			return;
		}
		PictureData image = pictures.get(picId);
		byte[] data = image.getData();
		String fileName = "pic"+Math.round(Math.random()*100000000000L);
		fileName+= "."+ExcelPublicUtil.getFileExtendName(data);
		if(excelParams.get(titleString).getSaveType()==1){
			String path = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("\\") + 
			getSaveUrl(excelParams.get(titleString),object);
			path = path.replace("WEB-INF/classes/","");
			path = path.replace("file:/","");
			File savefile = new File(path);
			if(!savefile.exists()){
				savefile.mkdirs();
			}
			savefile = new File(path+"/"+fileName);
			FileCopyUtils.copy(data, savefile);
			setValues(excelParams.get(titleString),object,getSaveUrl(excelParams.get(titleString),object)+"/"+fileName);
		}else{
			setValues(excelParams.get(titleString),object,data);
		}
	}
	/**
	 * 获取保存的真实路径
	 * @param excelImportEntity
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private static String getSaveUrl(ExcelImportEntity excelImportEntity,
			Object object) throws Exception {
		String url = "";
		if(excelImportEntity.getSaveUrl().equals("upload")){
			if(excelImportEntity.getSetMethods()!=null
					&&excelImportEntity.getSetMethods().size()>0){
				object = getFieldBySomeMethod(excelImportEntity.getSetMethods(), object);
			}
			url =  object.getClass().getName().split("\\.")[object.getClass().getName().split("\\.").length-1];
			return excelImportEntity.getSaveUrl()+"/"+url.substring(0, url.lastIndexOf("Entity"));
		}
		return excelImportEntity.getSaveUrl();
	}

	/***
	 * 向List里面继续添加元素
	 * 
	 * @param object
	 * @param param
	 * @param row
	 * @param titlemap 
	 * @param targetId
	 * @param params 
	 * @param currentImageIndex 
	 * @param pictures 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void addListContinue(Object object,
			ExcelCollectionParams param, Row row,
			Map<Integer, String> titlemap, String targetId,
			Map<String, PictureData> pictures,ImportParams params) throws Exception {
		Collection collection = (Collection) ExcelPublicUtil.getMethod(
				param.getName(), object.getClass()).invoke(object,  new Object[] {});
		Object entity = ExcelPublicUtil.createObject(param.getType(), targetId);
		String picId;
		boolean isUsed = false;//是否需要加上这个对象
		for(int i =row.getFirstCellNum() ;i<row.getLastCellNum();i++){
			Cell cell = row.getCell(i);
			String titleString = (String) titlemap.get(i);
			if (param.getExcelParams().containsKey(titleString)) {
				if(param.getExcelParams().get(titleString).getType()==2){
					picId = row.getRowNum()+"_"+i;
					saveImage(object,picId,param.getExcelParams(),
							titleString,pictures,params);
				}else{
					judgeTypeAndSetValue(entity, cell, param.getExcelParams(), titleString);
				}
				isUsed = true;
			}
		}
		if(isUsed){
			collection.add(entity);
		}
	}

	/**
	 * 设置值
	 * 
	 * @param object
	 * @param excelParams
	 * @param cell
	 * @param titleString
	 */
	private static void judgeTypeAndSetValue(Object object, Cell cell,
			Map<String, ExcelImportEntity> excelParams, String titleString)
			throws Exception {
		ExcelImportEntity entity = excelParams.get(titleString);
		Method setMethod = entity.getSetMethods()!=null&&entity.getSetMethods().size() > 0 ? entity
				.getSetMethods().get(entity.getSetMethods().size() - 1)
				: entity.getSetMethod();
		Type[] ts = setMethod.getGenericParameterTypes();
		String xclass = ts[0].toString();
		
		//特殊类型提前判断
		if("Date".equals(entity.getImportCellType())){//Excel单元格的类型为Date,后台接收的是String
			Date cellDate = null;
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				// 日期格式
				cellDate = cell.getDateCellValue();
				setValues(entity, object, DateUtil.dateConvertStr(cellDate, DateConstant.Y_M_D));
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//去掉值左右两边的空格
				String value = cell.getStringCellValue();
				if(StringUtils.isNotEmpty(value)){
					value = value.trim();
				}
				cellDate = getDateData(entity,value);
				setValues(entity, object, cellDate);
			}
			return;
		}
		
		if (xclass.equals("class java.lang.String")) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			//去掉值左右两边的空格
			String value = cell.getStringCellValue();
			if(StringUtils.isNotEmpty(value)){
				value = value.trim();
			}
			setValues(entity, object, value);
		} else if (xclass.equals("class java.util.Date")) {
			Date cellDate = null;
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				// 日期格式
				cellDate = cell.getDateCellValue();
				setValues(entity, object, cellDate);
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//去掉值左右两边的空格
				String value = cell.getStringCellValue();
				if(StringUtils.isNotEmpty(value)){
					value = value.trim();
				}
				cellDate = getDateData(entity,value);
				setValues(entity, object, cellDate);
			}
		} else if (xclass.equals("class java.lang.Boolean")) {
			boolean valBool;
			if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
				valBool = cell.getBooleanCellValue();
			} else {
				valBool = cell.getStringCellValue().equalsIgnoreCase("true")
						|| (!cell.getStringCellValue().equals("0"));
			}
			setValues(entity, object, valBool);
		} else if (xclass.equals("class java.lang.Integer")) {
			Integer valInt;
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				valInt = (new Double(cell.getNumericCellValue())).intValue();
			} else {
				valInt = new Integer(cell.getStringCellValue());
			}
			setValues(entity, object, valInt);
		} else if (xclass.equals("class java.lang.Long")) {
			Long valLong;
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				valLong = (new Double(cell.getNumericCellValue())).longValue();
			} else {
				valLong = new Long(cell.getStringCellValue());
			}
			setValues(entity, object, valLong);
		} else if (xclass.equals("class java.math.BigDecimal")) {
			BigDecimal valDecimal;
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				valDecimal = new BigDecimal(cell.getNumericCellValue());
			} else {
				valDecimal = new BigDecimal(cell.getStringCellValue());
			}
			setValues(entity, object, valDecimal);
		} else if (xclass.equals("class java.lang.Double") || "double".equals(xclass)) {
			Double valDouble = null;
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType() || Cell.CELL_TYPE_FORMULA == cell.getCellType()) {//数值或者表达式类型
				valDouble = new Double(cell.getNumericCellValue());
			} else {
				String sValue = cell.getStringCellValue();
				if(StringUtils.isNotEmpty(sValue)){
					try {
						if(sValue.indexOf(",") > -1){
							sValue = sValue.replace(",", "").replace(" ", "").trim();
						}
						valDouble = new Double(sValue);
					} catch (Exception e) {
						valDouble = 0d;
					}
				}else{
					valDouble = 0d;//如果Excel数值型，但没有填值，则后台默认赋值为0
				}
			}
			setValues(entity, object, valDouble);
		}

	}
	/**
	 * 获取日期类型数据
	 *@Author JueYue
	 *@date   2013年11月26日
	 *@param entity 
	 *@param stringCellValue
	 *@return
	 */
	private static Date getDateData(ExcelImportEntity entity, String value) {
		if(StringUtils.isNotEmpty(entity.getImportFormat())&&
				StringUtils.isNotEmpty(value)){
			SimpleDateFormat format = new SimpleDateFormat(entity.getImportFormat());
			try {
				return format.parse(value);
			} catch (ParseException e) {}
		}
		return null;
	}
	/**
	 * 
	 * @param entity
	 * @param object
	 * @param value
	 * @throws Exception
	 */
	private static void setValues(ExcelImportEntity entity, Object object,
			Object value) throws Exception {
		if (entity.getSetMethods() != null) {
			setFieldBySomeMethod(entity.getSetMethods(), object, value);
		} else {
			entity.getSetMethod().invoke(object, value);
		}
	}

	/**
	 * 多个get 最后再set
	 * 
	 * @param setMethods
	 * @param object
	 */
	private static void setFieldBySomeMethod(List<Method> setMethods,
			Object object, Object value) throws Exception {
		Object t = getFieldBySomeMethod(setMethods, object);
		setMethods.get(setMethods.size() - 1).invoke(t, value);
	}

	private static Object getFieldBySomeMethod(List<Method> list, Object t)
			throws Exception {
		Method m;
		for (int i = 0; i < list.size() - 1; i++) {
			m = list.get(i);
			t = m.invoke(t, new Object[] {});
		}
		return t;
	}

	/**
	 * 获取需要导出的全部字段
	 * 
	 * @param targetId
	 *            目标ID
	 * @param excelCollection
	 * @param filed
	 * @throws Exception
	 */
	private static void getAllExcelField(String targetId, Field[] fields,
			Map<String, ExcelImportEntity> excelParams,
			List<ExcelCollectionParams> excelCollection, Class<?> pojoClass,
			List<Method> getMethods) throws Exception {
		ExcelImportEntity excelEntity = null;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (ExcelPublicUtil.isNotUserExcelUserThis(field, targetId)) {
				continue;
			}
			if (ExcelPublicUtil.isCollection(field.getType())) {
				// 集合对象设置属性
				ExcelCollectionParams collection = new ExcelCollectionParams();
				collection.setName(field.getName());
				Map<String, ExcelImportEntity> temp = new HashMap<String, ExcelImportEntity>();
				ParameterizedType pt = (ParameterizedType) field
						.getGenericType();
				Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
				collection.setType(clz);
				getExcelFieldList(targetId,
						ExcelPublicUtil.getClassFields(clz), clz, temp, null);
				collection.setExcelParams(temp);
				excelCollection.add(collection);
			} else if (ExcelPublicUtil.isJavaClass(field)) {
				addEntityToMap(targetId, field, excelEntity, pojoClass,getMethods,excelParams);
			} else {
				List<Method> newMethods = new ArrayList<Method>();
				if (getMethods != null) {
					newMethods.addAll(getMethods);
				}
				newMethods.add(ExcelPublicUtil.getMethod(field.getName(),
						pojoClass));
				getAllExcelField(targetId,
						ExcelPublicUtil.getClassFields(field.getType()),
						excelParams, excelCollection, field.getType(),
						newMethods);
			}
		}
	}

	private static void getExcelFieldList(String targetId, Field[] fields,
			Class<?> pojoClass, Map<String, ExcelImportEntity> temp,
			List<Method> getMethods) throws Exception {
		ExcelImportEntity excelEntity = null;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (ExcelPublicUtil.isNotUserExcelUserThis(field, targetId)) {
				continue;
			}
			if (ExcelPublicUtil.isJavaClass(field)) {
				addEntityToMap(targetId, field, excelEntity, pojoClass,getMethods,temp);
			} else {
				List<Method> newMethods = new ArrayList<Method>();
				if (getMethods != null) {
					newMethods.addAll(getMethods);
				}
				newMethods.add(ExcelPublicUtil.getMethod(field.getName(),
						pojoClass,field.getType()));
				getExcelFieldList(targetId,
						ExcelPublicUtil.getClassFields(field.getType()),
						field.getType(), temp, newMethods);
			}
		}
	}
	/**
	 * 把这个注解解析放到类型对象中
	 * @param targetId
	 * @param field
	 * @param excelEntity
	 * @param excel
	 * @param pojoClass
	 * @param getMethods
	 * @param temp
	 * @throws Exception 
	 */
	private static void addEntityToMap(String targetId, Field field,
			ExcelImportEntity excelEntity, Class<?> pojoClass,
			List<Method> getMethods, Map<String, ExcelImportEntity> temp) throws Exception {
		Excel excel = field.getAnnotation(Excel.class);
		excelEntity = new ExcelImportEntity();
		excelEntity.setType(excel.exportType());
		excelEntity.setSaveUrl(excel.savePath());
		excelEntity.setSaveType(excel.imageType());
		excelEntity.setImportCellType(excel.importCellType());
		getExcelField(targetId, field, excelEntity, excel, pojoClass);
		if (getMethods != null) {
			List<Method> newMethods = new ArrayList<Method>();
			newMethods.addAll(getMethods);
			newMethods.add(excelEntity.getSetMethod());
			excelEntity.setSetMethods(newMethods);
		}
		temp.put(excelEntity.getName(), excelEntity);
		
	}

	private static void getExcelField(String targetId, Field field,
			ExcelImportEntity excelEntity, Excel excel, Class<?> pojoClass)
			throws Exception {
		excelEntity.setName(getExcelName(excel.exportName(), targetId));
		String fieldname = field.getName();
		if (excel.importConvertSign() == 1||excel.imExConvert()==1) {
			StringBuffer getConvertMethodName = new StringBuffer("convertSet");
			getConvertMethodName
					.append(fieldname.substring(0, 1).toUpperCase());
			getConvertMethodName.append(fieldname.substring(1));
			Method getConvertMethod = pojoClass.getMethod(
					getConvertMethodName.toString(), new Class[] {field.getType()});
			excelEntity.setSetMethod(getConvertMethod);
		} else {
			excelEntity.setSetMethod(ExcelPublicUtil.getMethod(fieldname,
					pojoClass,field.getType()));
		}
		if(StringUtils.isEmpty(excel.importFormat())){
			excelEntity.setImportFormat(excel.imExFormat());
		}else{
			excelEntity.setImportFormat(excel.importFormat());
		}
	}

	/**
	 * 判断在这个单元格显示的名称
	 * 
	 * @param exportName
	 * @param targetId
	 * @return
	 */
	private static String getExcelName(String exportName, String targetId) {
		if (exportName.indexOf("_") < 0) {
			return exportName;
		}
		String[] arr = exportName.split(",");
		for (String str : arr) {
			if (str.indexOf(targetId) != -1) {
				return str.split("_")[0];
			}
		}
		return null;
	}
	
	/**
	 * 处理导入后返回的校验结果信息
	 * @param j 前台接受的json对象
	 * @param result 导入后的结果
	 */
	public static void handleMess(Json j, ImportResult result) {
		List<String> errMess = result.getErrMess();
		
		StringBuffer message = new StringBuffer();
		if(errMess != null && errMess.size() > 0){
			for(String err : errMess){
				message.append(err).append("；<br>");
			}
		}
		
		int succSize = result.getSuccResult().size();
		if(succSize > 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
		
		if(errMess != null && errMess.size() > 0){//有校验失败的记录
			String msg = "文件校验成功：<font color='green'><strong>"+succSize+"</strong></font>笔，校验失败：<font color='red' weight='blod'><strong>"+result.getErrResult().size()+"</strong></font>笔，错误信息如下：<br>"+message;
			j.setMsg(msg);
		}else{
			j.setMsg("文件校验成功！<br>");
		}
	}
	
	public static <T> String getServiceErr(List<T> listCourses,
			List<String> serviceErr, int failCount) {
		StringBuffer message = new StringBuffer();
		String msg = "";
		if(serviceErr != null && serviceErr.size() > 0){
			for(String err : serviceErr){
				message.append(err).append("；<br>");
			}
			msg = "文件导入成功：<font color='green'><strong>"+(listCourses.size() - failCount)+"</strong></font>笔，导入失败：<font color='red' weight='blod'><strong>"+failCount+"</strong></font>笔，错误信息如下：<br>"+message;
		}else{
			if(listCourses.size() > 0){
				msg = "文件导入成功！";
			}else{
				msg = "文件导入失败！";
			}
		}
		return msg;
	}
	

}
