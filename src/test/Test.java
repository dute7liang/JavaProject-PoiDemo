package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.app.core.utility.excel.ExcelExportUtil;
import com.app.core.utility.excel.ExcelTitle;

public class Test {
	
	public static void main(String[] args) throws IOException {
		List<User> userList = new ArrayList<User>();
		String[] names = new String[]{"张","大说","例","四","手动","萨","大","大神","多少啊","吖吖","搭","年级","项目","手动","为","狗头人"};  //16
		int userInt = 50;
		System.out.println("开始生成随机人物:"+userInt+"人");
		for (int i = 0; i < userInt; i++) {
			//随机名字
			int nameInt = new Random().nextInt(1)+2;
			String name = "";
			for (int j = 0; j < nameInt; j++) {
				name += names[new Random().nextInt(16)];
			}
			//随机年龄
			int age = new Random().nextInt(100);
			//随机 资金
			double money = (new Random().nextInt(10)+1)*10000*new Random().nextDouble();
			userList.add(new User(name, age, money, new Date()));
		}
		System.out.println("随机人物生成结束");
		long start = new Date().getTime();
		System.out.println("开始导出"+new Date());
		ExcelTitle entity = new ExcelTitle();
		entity.setSheetName("zhangliangTest");
		
		HSSFWorkbook wb = ExcelExportUtil.exportExcel(entity, User.class, userList);
		OutputStream o = new FileOutputStream(new File("D:/test.xls"));
		wb.write(o);
		o.close();
		
		long end = new Date().getTime();
		System.out.println("导出结束"+new Date());
		System.out.println("总共耗时："+(end-start)+"毫秒");
	}
	
	
}
