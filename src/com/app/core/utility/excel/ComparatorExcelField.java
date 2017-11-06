package com.app.core.utility.excel;

import java.util.Comparator;

/**
 * 按照升序排序
 * @author hqb qb.huang@wescxx.com
 * @date 2015年10月13日 上午11:53:58 
 * @version V1.0
 */
public class ComparatorExcelField implements Comparator<ExcelExportEntity> {

	
	public int compare(ExcelExportEntity prev,ExcelExportEntity next) {
		return prev.getOrderNum() - next.getOrderNum();
	}

}
