package com.app.core.model;

/**   
* @Description: 常见日期枚举(特殊说明：DAYNAME:星期几名称;DAY_OF_WEEK:一周中的第几天,从星期天开始,周六结束)
* @author cdc dc.chen@wescxx.com   
* @date 2016年12月29日 下午3:16:38 
* @version V1.0   
*/
public enum DateConstant {
	YEAR_MONTH_DATE("yyyy-MM-dd"),
	WHOLE_TIME_WITH_SSS("yyyyMMddHHmmssSSS"),WHOLE_TIME_WITH_NO_SPLIT("yyyyMMddHHmmss"),
	H_S("HH:mm"),WHOLE_TIME_WITHOUT_S("yyyy-MM-dd HH:mm"),Y_M_D_CN("yyyy年MM月dd日"),
	
	WHOLE_TIME("yyyy-MM-dd HH:mm:ss"),YMD("yyyyMMdd"),
	Y_M_D("yyyy-MM-dd"),Y_M_D_("yyyy_MM_dd"),Y_M("yyyy-MM"),H_M_S("HH:mm:ss"),YM("yyyyMM"),
	YMD_ORA("yyyy/MM/dd"),H_M_S_WITH_SSS("HH:mm:ssSSS"),
	
	MONTH_DAY("MMdd"),YEAR("yyyy"),MONTH("MM"),DAY("dd"),DAYNAME("day_name"),DAY_OF_WEEK("day"),HOUR("hh"),MINUTE("mm"),
	SECOND("ss");
	
	private String code;
	
	private DateConstant(String _code){
		this.code = _code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String _code) {
		this.code = _code;
	}
}
