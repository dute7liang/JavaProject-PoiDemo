package com.app.core.utility.system;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.app.core.model.DateConstant;


/**
 * 
 * @Description: 日期处理工具类
 * @author hqb qb.huang@scxx.com
 * @date 2016年12月9日 下午2:57:00
 * @version V1.0
 */
/** 
* @Description: 
* @author cdc dc.chen@wescxx.com 
* @date 2017年6月5日 下午4:18:19 
* @version V1.0
*/
public class DateUtil extends DateUtils {

	// 一天的开始时间
	public final static String DAY_BEGIN = "00:00:00";

	// 一天的结束时间
	public final static String DAY_END = "23:59:59";

	// 以毫秒表示的时间
	private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final long HOUR_IN_MILLIS = 3600 * 1000;
	private static final long MINUTE_IN_MILLIS = 60 * 1000;
	private static final long SECOND_IN_MILLIS = 1000;

	/**
	 * 将字符串转换成日期
	 * 
	 * @param <T>
	 * @param strDate
	 * @param format
	 * @param clazz
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Date> T strConvertDate(String strDate, DateConstant format, Class<T> clazz)
			throws Exception {
		// Assert.assertNotNull("日期为空！", strDate);
		T date = null;
		SimpleDateFormat sd = new SimpleDateFormat(format.getCode());
		if (clazz == Date.class)
			date = (T) sd.parse(strDate);
		else
			date = clazz.getConstructor(long.class).newInstance(sd.parse(strDate).getTime());
		return date;
	}

	/**
	 * 字符串转换为日期,格式：yyyy-MM-dd
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Date strConvertDate(String strDate) throws Exception {
		return strConvertDate(strDate, DateConstant.Y_M_D, Date.class);
	}

	/**
	 * 字符串转换为日期
	 * @param strDate
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date strConvertDate(String strDate, DateConstant format) throws Exception {
		return strConvertDate(strDate, format, Date.class);
	}

	/**
	 * 将日期转换成字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @return
	 */
	public static final <T extends Date> String dateConvertStr(T date, DateConstant format) {
		SimpleDateFormat sd = new SimpleDateFormat(format.getCode());
		String str = sd.format(date);
		return str;
	}

	/**
	 * 取得指定日期的下一日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一日
	 */
	public static java.util.Date getNextDate(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的前面第N个日期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 取得指定日期的前面第N个日期
	 */
	public static java.util.Date getBefrNDate(java.util.Date date, int amount) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -amount);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的后面第N个日期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 取得指定日期的前第N个日期
	 */
	public static java.util.Date getAfterNDate(java.util.Date date, int amount) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, amount);
		return gc.getTime();
	}

	/**
	 * 
	 * 
	 * @param 10位的日期
	 *            yyyy-MM-dd
	 * 
	 * @return 8位的日期 yyyyMMdd
	 */
	public static String _date10To8(String date_10) {
		String date_8 = "";
		if (date_10 == null || "".equals(date_10) || date_10.length() != 10) {
			date_8 = "";
		} else {
			String[] r = date_10.split("-");
			for (int i = 0; i < r.length; i++) {
				date_8 += r[i];
			}
		}
		return date_8;
	}

	/**
	 * @param 8位的日期
	 *            yyyyMMdd
	 * 
	 * @return 6位的日期 yyMMdd
	 */
	public static String _date8To6(String date_8) {
		String date_6 = "";
		if (date_8 == null || "".equals(date_8) || date_8.length() != 8) {
			date_6 = "";
		} else {
			date_6 = date_8.substring(2, 8);
		}
		return date_6;
	}

	/**
	 * 
	 * 
	 * @param 8位的日期
	 *            yyyyMMdd
	 * 
	 * @return 10位的日期 yyyy-MM-dd
	 */
	public static String _date8To10(String date_8) {
		String date_10 = "";
		if (date_8 == null || "".equals(date_8) || date_8.length() != 8) {
			date_10 = "";
		} else {
			String y = date_8.substring(0, 4);
			String m = date_8.substring(4, 6);
			String d = date_8.substring(6, 8);
			date_10 = y + "-" + m + "-" + d;
		}
		return date_10;
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置周一
		c.setFirstDayOfWeek(Calendar.MONDAY);

		return c.getTime();
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}
	/**
	 * 获取某月的第一天
	 * @Title:getLastDayOfMonth
	 * @param:@param year
	 * @param:@param month
	 * @throws
	 */
	public static Date getFirstDayOfMonth(String dateStr){
		if(StringUtil.isEmpty(dateStr))return null;
		String[] dateArr = dateStr.split("-");
		int year = 2017;
		int month = 7;
		if(dateArr.length > 0){
			year = Integer.valueOf(dateArr[0]);
		}
		if(dateArr.length > 1){
			month = Integer.valueOf(dateArr[1]);
		}
		return getFirstDayOfMonth(year, month);
	}
	public static Date getLastDayOfMonth(String dateStr){
		if(StringUtil.isEmpty(dateStr))return null;
		String[] dateArr = dateStr.split("-");
		int year = 2017;
		int month = 7;
		if(dateArr.length > 0){
			year = Integer.valueOf(dateArr[0]);
		}
		if(dateArr.length > 1){
			month = Integer.valueOf(dateArr[1]);
		}
		return getLastDayOfMonth(year, month);
	}
	public static Date getFirstDayOfMonth(int year,int month)
	{
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//获取某月最大天数
		int lastDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String lastDayOfMonth = sdf.format(cal.getTime());
		
		return cal.getTime();
	}
    /**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @param:@param year
     * @param:@param month
     * @throws
     */
    public static Date getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // String lastDayOfMonth = sdf.format(cal.getTime());
         
        return cal.getTime();
    }
    
    /**
     * 获取当月的最后一天
     * @return Date
     */
    public static Date getLastDayOfCurMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastDate = format.format(cale.getTime());
		try {
			return format.parse(lastDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }

	/**
	 * 获取参数日期是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 月份转换类 使其满足两位数字
	 * 
	 * @param startNum
	 * @return
	 */
	public static String formatStr(String startNum) {
		String setcode = startNum;
		if (startNum.length() == 1) {
			setcode = "0" + startNum;
		}
		return setcode;
	}

	/**
	 * 功能说明：加减日期
	 * 
	 * @param 日期对象
	 * @param amount是天数
	 *            负数则为减
	 * @return 修改后的日期
	 */
	public static Date addAndMinusDate(Date dDate, int amount) {
		long initDate = dDate.getTime();
		// T + N日期
		initDate = initDate + (amount * 24 * 3600 * 1000);
		// N的具体日期
		Date fendDate = new Date(initDate);
		return fendDate;
	}

	/**
	 * 功能说明：加减日期
	 * 
	 * @param 日期对象
	 * @param minutes是分钟数
	 *            负数则为减
	 * @return 修改后的日期
	 */
	public static Date addAndMinusMinutes(Date dDate, double minutes) {
		long initDate = dDate.getTime();
		// T + N日期
		initDate = initDate + (long) (minutes * 60 * 1000);
		// N的具体日期
		Date fendDate = new Date(initDate);
		return fendDate;
	}

	private static int nextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}

	/**
	 * 生成序列号
	 * 
	 * @return
	 */
	public static String getSeqNO(DateConstant format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format.getCode());
		String strDate = sdf.format(new Date());
		String seqNo = strDate + nextInt(1000000, 9999999);
		return "01" + seqNo;
	}

	public static int getDateInt(Date date) {
		return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(date));
	}

	public static String getDateString(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	public static String getCurrentDate(DateConstant format) {
		return new SimpleDateFormat(format.getCode()).format(new Date());
	}

	public static String getTimeString(Date date) {
		return new SimpleDateFormat("HHmmss").format(date);
	}

	public static int getTimeInt(Date date) {
		return Integer.valueOf(new SimpleDateFormat("HHmmss").format(date));
	}

	public static String getSeqNo() {
		long min = 100000000001L;
		long max = 999999999999L;
		String result = "";
		for (long i = min; i < max; i++) {
			result = String.valueOf(i);
		}
		min = min + 1;
		return result;
	}

	/**
	 * 功能说明：返回两个日期相差date2-date1的天数
	 * 
	 * @param dDate1
	 *            ：日期
	 * @param dDate2
	 *            ：被减日期
	 * @return 日期相差的天数值
	 */
	public static int dateDiff(Date dDate1, Date dDate2) {
		int year = 0;
		int month = 0;
		int day = 0;
		GregorianCalendar cl1 = new GregorianCalendar();
		GregorianCalendar cl2 = null;

		cl1.setTime(dDate2);
		year = cl1.get(Calendar.YEAR);
		month = cl1.get(Calendar.MONTH);
		day = cl1.get(Calendar.DAY_OF_MONTH);
		cl2 = new GregorianCalendar(year, month, day);

		cl1.setTime(dDate1);
		year = cl1.get(Calendar.YEAR);
		month = cl1.get(Calendar.MONTH);
		day = cl1.get(Calendar.DAY_OF_MONTH);
		cl1.clear();
		cl1.set(year, month, day);
		return (int) NumUtil.round(NumUtil.sub(cl2.getTimeInMillis(), cl1.getTimeInMillis()) / DAY_IN_MILLIS, 0);
	}

	/**
	 * 获取当前年
	 * 
	 * @param dDate
	 * @return
	 */
	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	/**
	 * 获取月份
	 * @return
	 */
	public static int getMonth(){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		return month+1;
	}

	/**
	 * 获取两个日期间的日差数组
	 * 
	 * @param newStartDate
	 *            2011-04-29
	 * @param newStartDate
	 *            2011-05-01
	 * @return String[] {2011-04-29,2011-04-30,2011-05-01}
	 */
	public static String[] getArrDate(String newStartDate, String newEndDate) throws Exception {
		Date startDate = strConvertDate(newStartDate);
		Date endDate = strConvertDate(newEndDate);

		String[] arrDate = new String[dateDiff(startDate, endDate) + 1];
		arrDate[0] = dateConvertStr(startDate, DateConstant.YMD);
		for (int i = 1; i < arrDate.length; i++) {
			arrDate[i] = dateConvertStr(getNextDate(strConvertDate(arrDate[i - 1])), DateConstant.YMD);
		}
		return arrDate;
	}

	/**
	 * 取得日期的指定下一日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一日
	 */
	public static java.util.Date nextDay(java.util.Date date, int num) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, num);
		return gc.getTime();
	}

	public static String[] getArrDate1(String newStartDate, String newEndDate, boolean sign) throws Exception {
		Date startDate = strConvertDate(newStartDate);
		Date endDate = strConvertDate(newEndDate);
		String[] arrDate = null;
		if (sign) {
			arrDate = new String[dateDiff(startDate, endDate) + 1];
		} else {
			arrDate = new String[dateDiff(startDate, endDate)];
		}
		arrDate[0] = dateConvertStr(startDate, DateConstant.YMD);
		for (int i = 1; i < arrDate.length; i++) {
			arrDate[i] = dateConvertStr(getNextDate(strConvertDate(arrDate[i - 1])), DateConstant.YMD);
		}
		return arrDate;
	}

	public static String dateToString(Date date, DateConstant format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format.getCode());
		try {
			result = formater.format(date);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 将时间字符串(HH:mm:ss)转化为quartz可识别的时间表达式(* * * * * ?)
	 * 
	 * @param time
	 * @return
	 */
	public static String timeToCronExp(String time) {
		StringBuilder result = new StringBuilder("");
		String[] arr = time.split(":");
		if (arr.length == 3) {
			String hour = Integer.parseInt(arr[0]) < 10 ? (arr[0].replaceFirst("0", "")) : arr[0];
			String minute = Integer.parseInt(arr[1]) < 10 ? (arr[1].replaceFirst("0", "")) : arr[1];
			String second = Integer.parseInt(arr[2]) < 10 ? (arr[2].replaceFirst("0", "")) : arr[2];

			result.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append("*")
					.append(" ").append("*").append(" ").append("?");
		}

		return result.toString();

	}

	/**
	 * 将quartz时间表达式(* * * * * ?)转化为时间字符串(HH:mm:ss)
	 * 
	 * @param cronExp
	 * @return
	 */
	public static String cronExpToTime(String cronExp) {
		StringBuilder result = new StringBuilder("");
		String[] arr = cronExp.split(" ");
		if (arr.length == 6) {
			String hour = arr[2].length() != 2 ? ("0" + arr[2]) : arr[2];
			String minute = arr[1].length() != 2 ? ("0" + arr[1]) : arr[1];
			String second = arr[0].length() != 2 ? ("0" + arr[0]) : arr[0];

			result.append(hour).append(":").append(minute).append(":").append(second);
		}
		return result.toString();
	}

	// 指定模式的时间格式
	private static SimpleDateFormat getSDFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 当前日历，这里用中国时间表示
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 指定毫秒数表示的日历
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日历
	 */
	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(millis));
		return cal;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getDate
	// 各种方式获取的Date
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 当前日期
	 * 
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 指定毫秒数表示的日期
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日期
	 */
	public static Date getDate(long millis) {
		return new Date(millis);
	}

	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timestamptoStr(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(new SimpleDateFormat(DateConstant.Y_M_D.getCode()));
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, new SimpleDateFormat(DateConstant.Y_M_D.getCode()));
		return new Timestamp(date.getTime());
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(SimpleDateFormat date_sdf) {
		Date date = getDate();
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateformat(String date, DateConstant format) {
		SimpleDateFormat sformat = new SimpleDateFormat(format.getCode());
		Date _date = null;
		try {
			_date = sformat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sformat.format(_date);
	}

	/**
	 * 格式化时间字符串
	 * 
	 * @param date
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 */
	public static String dateStrFormat(String dateStr, DateConstant oldFormat, DateConstant newFormat) {
		String newDateStr = "";
		if (StringUtils.isNotEmpty(dateStr)) {
			try {
				newDateStr = new SimpleDateFormat(newFormat.getCode())
						.format(new SimpleDateFormat(oldFormat.getCode()).parse(dateStr));
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
		}
		return newDateStr;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, SimpleDateFormat date_sdf) {
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String getDate(DateConstant format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format.getCode());
		return sdf.format(date);
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestamp(long millis) {
		return new Timestamp(millis);
	}

	/**
	 * 以字符形式表示的时间戳
	 * 
	 * @param time
	 *            毫秒数
	 * @return 以字符形式表示的时间戳
	 */
	public static Timestamp getTimestamp(String time) {
		return new Timestamp(Long.parseLong(time));
	}

	/**
	 * 系统当前的时间戳
	 * 
	 * @return 系统当前的时间戳
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 指定日期的时间戳
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 指定日历的时间戳
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar cal) {
		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp gettimestamp() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
		return buydate;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getMillis
	// 各种方式获取的Millis
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 系统时间的毫秒数
	 * 
	 * @return 系统时间的毫秒数
	 */
	public static long getMillis() {
		return new Date().getTime();
	}

	/**
	 * 指定日历的毫秒数
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的毫秒数
	 */
	public static long getMillis(Calendar cal) {
		return cal.getTime().getTime();
	}

	/**
	 * 指定日期的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的毫秒数
	 */
	public static long getMillis(Date date) {
		return date.getTime();
	}

	/**
	 * 指定时间戳的毫秒数
	 * 
	 * @param ts
	 *            指定时间戳
	 * @return 指定时间戳的毫秒数
	 */
	public static long getMillis(Timestamp ts) {
		return ts.getTime();
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatDate
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	public static String formatDate_01() {
		return new SimpleDateFormat(DateConstant.Y_M_D_CN.getCode()).format(getCalendar().getTime());
	}

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日
	 * 
	 * @return 默认日期按“年-月-日“格式显示
	 */
	public static String formatDate() {
		return new SimpleDateFormat(DateConstant.Y_M_D.getCode()).format(getCalendar().getTime());
	}

	public static String getDateTime() {
		return new SimpleDateFormat(DateConstant.WHOLE_TIME.getCode()).format(new Date());
	}

	/**
	 * 获取时间字符串
	 */
	public static String getDataString(SimpleDateFormat formatstr) {
		return formatstr.format(getCalendar().getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Calendar cal) {
		return new SimpleDateFormat(DateConstant.Y_M_D.getCode()).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Date date) {
		return new SimpleDateFormat(DateConstant.Y_M_D.getCode()).format(date);
	}

	public static String formatDate_wz(Date date) {
		return new SimpleDateFormat(DateConstant.Y_M_D_CN.getCode()).format(date);
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日“格式显示
	 */
	public static String formatDate(long millis) {
		return new SimpleDateFormat(DateConstant.Y_M_D.getCode()).format(new Date(millis));
	}

	/**
	 * 默认日期按指定格式显示
	 * 
	 * @param pattern
	 *            指定的格式
	 * @return 默认日期按指定格式显示
	 */
	public static String formatDate(DateConstant dateConstant) {
		return getSDFormat(dateConstant.getCode()).format(getCalendar().getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 * 
	 * @param cal
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Calendar cal, String pattern) {
		return getSDFormat(pattern).format(cal.getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 * 
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Date date, DateConstant pattern) {
		return getSDFormat(pattern.getCode()).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
	 * 
	 * @return 默认日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime() {
		return new SimpleDateFormat(DateConstant.WHOLE_TIME_WITHOUT_S.getCode()).format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(long millis) {
		return new SimpleDateFormat(DateConstant.WHOLE_TIME_WITHOUT_S.getCode()).format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Calendar cal) {
		return new SimpleDateFormat(DateConstant.WHOLE_TIME_WITHOUT_S.getCode()).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Date date) {
		return new SimpleDateFormat(DateConstant.WHOLE_TIME_WITHOUT_S.getCode()).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatShortTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：时：分
	 * 
	 * @return 默认日期按“时：分“格式显示
	 */
	public static String formatShortTime() {
		return new SimpleDateFormat(DateConstant.H_S.getCode()).format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：时：分
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“时：分“格式显示
	 */
	public static String formatShortTime(long millis) {
		return new SimpleDateFormat(DateConstant.H_S.getCode()).format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Calendar cal) {
		return new SimpleDateFormat(DateConstant.H_S.getCode()).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Date date) {
		return new SimpleDateFormat(DateConstant.H_S.getCode()).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// parseDate
	// parseCalendar
	// parseTimestamp
	// 将字符串按照一定的格式转化为日期或时间
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Date parseDate(String src, DateConstant pattern) throws ParseException {
		return getSDFormat(pattern.getCode()).parse(src);

	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Calendar parseCalendar(String src, DateConstant pattern) throws ParseException {

		Date date = parseDate(src, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String formatAddDate(String src, DateConstant pattern, int amount) throws ParseException {
		Calendar cal;
		cal = parseCalendar(src, pattern);
		cal.add(Calendar.DATE, amount);
		return formatDate(cal);
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Timestamp parseTimestamp(String src, DateConstant pattern) throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// dateDiff
	// 计算两个日期之间的差值
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 * 
	 * @param flag
	 *            计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc
	 *            减数
	 * @param calDes
	 *            被减数
	 * @return 两个日期之间的差值
	 */
	public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

		long millisDiff = getMillis(calSrc) - getMillis(calDes);

		if (flag == 'y') {
			return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
		}

		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}

		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}

		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}

		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}

		return 0;
	}

	/**
	 * 功能说明：返回日期中的任何元素
	 * 
	 * @param field：Calendar类中的常数，如YEAR/MONTH/DAY_OF_MONTH...
	 *            <br>
	 *            <b><big>注意返回的month一月份是从0开始的！</big></b>
	 */
	public static final <T extends Date> int getDateItems(Date dDate, int field) {
		GregorianCalendar cl = new GregorianCalendar();
		cl.setTime(dDate);
		return cl.get(field);
	}

	public static final <T extends Date> int getYear(T date) {
		return getDateItems(date, Calendar.YEAR);
	}
	
	public static final <T extends Date> int getMonth(T date){
		return getDateItems(date, Calendar.MONTH)+1;
	}

	/**
	 * 获取当前服务器日期
	 * 
	 * @return
	 */
	public static java.util.Date getCurDate() {
		return new java.util.Date();
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getFormatDate(Date date, DateConstant format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format.getCode());
		try {
			return sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean checkDateTimeIsValid(Date time) {
		return !(time == null || "0000-00-00 00:00:00".equals(time.toString()));
	}

	public static Date dateFullTime2Date(Date date) {
		if (!checkDateTimeIsValid(date)) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateConstant.WHOLE_TIME.getCode());
		String s = simpleDateFormat.format(date);
		Date newDate = null;
		try {
			newDate = simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static Date addAndMinHmsDate(Date dDate, int hour, int min, int s) {
		return addAndMinDHmsDate(dDate, 0, hour, min, s);
	}

	public static Date addAndMinDHmsDate(Date dDate, int day, int hour, int min, int s) {
		long initDate = dDate.getTime();
		// T + N日期
		initDate = initDate + (day * 24 * 3600 * 1000) + (hour * 3600 * 1000) + (min * 60 * 1000) + (s * 1000);
		// N的具体日期
		Date fendDate = new Date(initDate);
		return fendDate;
	}

	/**
	 * 根据当前日期返回所在星期的某一天日期(默认星期一为一周的第一天)
	 * 
	 * @author zhouhao
	 * @param current
	 * @param target
	 *            第几天
	 * @return
	 */
	public static Date getDayOfWeekByOrderNum(Date current, int target) {
		if (target < 1 || target > 7) {
			throw new IllegalArgumentException("目标序号错误！");
		}
		Date monday = getMondayOfWeek(current);// 星期一

		Calendar cal = Calendar.getInstance();
		cal.setTime(monday);
		cal.add(Calendar.DATE, target - 1);

		return cal.getTime();
	}

	/**
	 * 获取日期所在周的星期一的日期
	 * 
	 * @author zhouhao
	 * @param current
	 * @return
	 */
	public static Date getMondayOfWeek(Date current) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(current);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		int order = -1;// 偏移量
		if (dayOfWeek == 1) {
			order = -6;
		} else {
			order = 2 - dayOfWeek;
		}
		cal.add(Calendar.DATE, order);
		return cal.getTime();
	}

	/**
	 * 将秒数时间间隔转换为hh:mm:ss 形式的字符串
	 * 
	 * @param seconds
	 *            秒数(整数)
	 * @return
	 */
	public static String secondToTime(int seconds) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (seconds <= 0)
			return "00:00:00";
		else {
			minute = seconds / 60;
			if (minute < 60) {
				second = seconds % 60;
				timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = seconds - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	private static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
	
	//*********以下是将日期转换成中文小写的方法：***************
	
	 /**
	  * create date:2010-5-22下午04:29:37
	  * 描述：将日期转换为指定格式字符串
	  * @param date  日期
	  * @return
	  */
	  public static String getDateStr(Date date)
	  {
	  SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
	  String datestr = sdf.format(date);
	  return datestr;
	  }
	  /**
	  * create date:2010-5-22下午03:40:44
	  * 描述：取出日期字符串中的年份字符串
	  * @param str 日期字符串
	  * @return
	  */
	  public static String getYearStr(String str)
	  {
	  String yearStr = "";
	  yearStr = str.substring(0,4);
	  return yearStr;
	  }
	   
	  /**
	  * create date:2010-5-22下午03:40:47
	  * 描述：取出日期字符串中的月份字符串
	  * @param str日期字符串
	  * @return
	  */
	  public static String getMonthStr(String str)
	  {
	  String monthStr;
	  int startIndex = str.indexOf("年");
	  int endIndex = str.indexOf("月");
	  monthStr = str.substring(startIndex+1,endIndex);
	  return monthStr;
	  }
	 
	    /**
	      * create date:2010-5-22下午03:32:31
	      * 描述：将源字符串中的阿拉伯数字格式化为汉字
	      * @param sign 源字符串中的字符
	      * @return
	      */
	    public static char formatDigit(char sign){
	        if(sign == '0')
	            sign = '0';
	        if(sign == '1')
	            sign = '一';
	        if(sign == '2')
	            sign = '二';
	        if(sign == '3')
	            sign = '三';
	        if(sign == '4')
	            sign = '四';
	        if(sign == '5')
	            sign = '五';
	        if(sign == '6')
	            sign = '六';
	        if(sign == '7')
	            sign = '七';
	        if(sign == '8')
	            sign = '八';
	        if(sign == '9')
	            sign = '九';
	        return sign;
	    }
	   
	    /**
	      * create date:2010-5-22下午03:31:51
	      * 描述： 获得月份字符串的长度
	      * @param str  待转换的源字符串
	      * @param pos1 第一个'-'的位置
	      * @param pos2 第二个'-'的位置
	      * @return
	      */
	    public static int getMidLen(String str,int pos1,int pos2){
	        return str.substring(pos1+1, pos2).length();
	    }
	    /**
	      * create date:2010-5-22下午03:32:17
	      * 描述：获得日期字符串的长度
	      * @param str  待转换的源字符串
	      * @param pos2 第二个'-'的位置
	      * @return
	      */
	    public static int getLastLen(String str,int pos2){
	        return str.substring(pos2+1).length();
	    }
	   
	  /**
	  * create date:2010-5-22下午03:40:50
	  * 描述：取出日期字符串中的日字符串
	  * @param str 日期字符串
	  * @return
	  */
	  public static String getDayStr(String str)
	  {
	  String dayStr = "";
	  int startIndex = str.indexOf("月");
	  int endIndex = str.indexOf("日");
	  dayStr = str.substring(startIndex+1,endIndex);
	  return dayStr;
	  }
	    /**
	      * create date:2010-5-22下午03:32:46
	      * 描述：格式化日期
	      * @param str 源字符串中的字符
	      * @return
	      */
	    public static String formatWholeStr(String str){
	        StringBuffer sb = new StringBuffer();
	        int pos1 = str.indexOf("-");
	        int pos2 = str.lastIndexOf("-");
	        for(int i = 0; i < 4; i++){
	            sb.append(formatDigit(str.charAt(i)));
	        }
	        sb.append('年');
	        if(getMidLen(str,pos1,pos2) == 1){
	            sb.append(formatDigit(str.charAt(5))+"月");
	            if(str.charAt(7) != '0'){
	                if(getLastLen(str, pos2) == 1){
	                    sb.append(formatDigit(str.charAt(7))+"日");
	                }
	                if(getLastLen(str, pos2) == 2){
	                    if(str.charAt(7) != '1' && str.charAt(8) != '0'){
	                        sb.append(formatDigit(str.charAt(7))+"十"+formatDigit(str.charAt(8))+"日");
	                    }
	                    else if(str.charAt(7) != '1' && str.charAt(8) == '0'){
	                        sb.append(formatDigit(str.charAt(7))+"十日");
	                    }
	                    else if(str.charAt(7) == '1' && str.charAt(8) != '0'){
	                        sb.append("十"+formatDigit(str.charAt(8))+"日");
	                    }
	                    else{
	                        sb.append("十日");
	                    }
	                }
	            }
	            else{
	                sb.append(formatDigit(str.charAt(8))+"日");
	            }
	        }
	        if(getMidLen(str,pos1,pos2) == 2){
	            if(str.charAt(5) != '0' && str.charAt(6) != '0'){
	                sb.append("十"+formatDigit(str.charAt(6))+"月");
	                if(getLastLen(str, pos2) == 1){
	                    sb.append(formatDigit(str.charAt(8))+"日");
	                }
	                if(getLastLen(str, pos2) == 2){
	                    if(str.charAt(8) != '0'){
	                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
	                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
	                        }
	                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
	                            sb.append(formatDigit(str.charAt(8))+"十日");
	                        }
	                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
	                            sb.append("十"+formatDigit(str.charAt(9))+"日");
	                        }
	                        else{
	                            sb.append("十日");
	                        }
	                    }
	                    else{
	                        sb.append(formatDigit(str.charAt(9))+"日");
	                    }
	                }
	            }
	            else if(str.charAt(5) != '0' && str.charAt(6) == '0'){
	                sb.append("十月");
	                if(getLastLen(str, pos2) == 1){
	                    sb.append(formatDigit(str.charAt(8))+"日");
	                }
	                if(getLastLen(str, pos2) == 2){
	                    if(str.charAt(8) != '0'){
	                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
	                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
	                        }
	                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
	                            sb.append(formatDigit(str.charAt(8))+"十日");
	                        }
	                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
	                            sb.append("十"+formatDigit(str.charAt(9))+"日");
	                        }
	                        else{
	                            sb.append("十日");
	                        }
	                    }
	                    else{
	                        sb.append(formatDigit(str.charAt(9))+"日");
	                    }
	                }
	            }
	            else{
	                sb.append(formatDigit(str.charAt(6))+"月");
	                if(getLastLen(str, pos2) == 1){
	                    sb.append(formatDigit(str.charAt(8))+"日");
	                }
	                if(getLastLen(str, pos2) == 2){
	                    if(str.charAt(8) != '0'){
	                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
	                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
	                        }
	                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
	                            sb.append(formatDigit(str.charAt(8))+"十日");
	                        }
	                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
	                            sb.append("十"+formatDigit(str.charAt(9))+"日");
	                        }
	                        else{
	                            sb.append("十日");
	                        }
	                    }
	                    else{
	                        sb.append(formatDigit(str.charAt(9))+"日");
	                    }
	                }
	            }
	        }
	        return sb.toString();
	    }
	    
	    
	    /** 
	     *  
	     *  取得一年的第几季度
	     *  
	     * @param date 
	     * @return 
	     */  
	    public static int getSeason(Date date) {  
	  
	        int season = 0;  
	  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        int month = c.get(Calendar.MONTH);  
	        switch (month) {  
	        case Calendar.JANUARY:  
	        case Calendar.FEBRUARY:  
	        case Calendar.MARCH:  
	            season = 1;  
	            break;  
	        case Calendar.APRIL:  
	        case Calendar.MAY:  
	        case Calendar.JUNE:  
	            season = 2;  
	            break;  
	        case Calendar.JULY:  
	        case Calendar.AUGUST:  
	        case Calendar.SEPTEMBER:  
	            season = 3;  
	            break;  
	        case Calendar.OCTOBER:  
	        case Calendar.NOVEMBER:  
	        case Calendar.DECEMBER:  
	            season = 4;  
	            break;  
	        default:  
	            break;  
	        }  
	        return season;  
	    } 
	    
	    /**
	     * 取得当前时间所在季度的第一天
	     * @return
	     */
	    public static Date getFirstDayOfSeason(Date date){
	    	Calendar calendar = new GregorianCalendar();
	    	calendar.setTime(date);
	    	int season = getSeason(date);
	    	switch (season){
		    	case 1:
		    		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		    		break;
		    	case 2:
		    		calendar.set(Calendar.MONTH, Calendar.APRIL);
		    		break;
		    	case 3:
		    		calendar.set(Calendar.MONTH, Calendar.JULY);
		    		break;
		    	case 4:
		    		calendar.set(Calendar.MONTH, Calendar.OCTOBER);
		    		break;
	    	}
	    	calendar.set(Calendar.DAY_OF_MONTH, 1);
	    	return calendar.getTime();
	    }
	    
	    /**
	     * 取得当前时间所在季度的最后一天
	     * @param date
	     * @return
	     */
	    public static Date getLastDayOfSeason(Date date){
	    	Calendar calendar = new GregorianCalendar();
	    	calendar.setTime(date);
	    	int season = getSeason(date);
	    	switch (season){
	    	case 1:
	    		calendar.set(Calendar.MONTH, Calendar.MARCH);
	    		break;
	    	case 2:
	    		calendar.set(Calendar.MONTH, Calendar.JUNE);
	    		break;
	    	case 3:
	    		calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
	    		break;
	    	case 4:
	    		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
	    		break;
	    	}
	    	calendar.add(Calendar.MONTH, 1);
	    	calendar.set(Calendar.DAY_OF_MONTH, 0);
	    	return calendar.getTime();
	    }
	    
	    /** 
	     * 取得一年的第几周 
	     *  
	     * @param date 
	     * @return 
	     */  
	    public static int getWeekOfYear(Date date) {  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        int week_of_year = c.get(Calendar.WEEK_OF_YEAR);  
	        return week_of_year;  
	    }  
	    
	    /** 
	     * 取得一年的第几月
	     *  
	     * @param date 
	     * @return 
	     */  
	    public static int getMonthOfYear(Date date) {  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        int month_of_year = c.get(Calendar.MONTH) + 1;  
	        return month_of_year;  
	    }  
	    
	    
	    /**
	     * 根据日期长度转换日期格式
	     * @param date
	     * @param len
	     * @return
	     */
	    public static Date strConvertDateByLen(String date){
    		try {
    			int len = date.length();
    			if(len == 8){
    				return strConvertDate(date, DateConstant.YMD);
	    		}else if (len == 10){
	    			return strConvertDate(date, DateConstant.Y_M_D);
	    		}
			} catch (Exception e) {
				e.printStackTrace();	
			}
			return null;
	    }
}
