package com.app.core.utility.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 提供高精度的运算支持. 所以函数以double为参数类型，兼容int与float. numberUtils:
 * 包含了字符串转化为数字的操作、最大最小值的比较 字符串是否为数字的判断等
 * 
 */
public class NumUtil {

	private NumUtil() {

	}
	
	public static final double parseDouble(Double d){
		return d == null ? 0 : d;
	}

	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static final double add(Double v1, Double v2) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null)
			v2= new Double(0) ;
		return add(v1, v2, 0, 0, 0, 0, 2);
	}

	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @param v3
	 *            加数
	 * @return 三个参数的和
	 */
	public static final double add(Double v1, Double v2, Double v3) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null)
			v2= new Double(0) ;
		if(v3==null)
			v3= new Double(0) ;
		return add(v1, v2, v3, 0, 0, 0, 3);
	}

	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @param v3
	 *            加数
	 * @param v4
	 *            加数
	 * @return 四个参数的和
	 */
	public static final double add(Double v1, Double v2, Double v3, Double v4) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null)
			v2= new Double(0) ;
		if(v3==null)
			v3= new Double(0) ;
		if(v4==null)
			v4= new Double(0);
		return add(v1, v2, v3, v4, 0, 0, 4);
	}

	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @param v3
	 *            加数
	 * @param v4
	 *            加数
	 * @param v5
	 *            加数
	 * @return 五个参数的和
	 */
	public static final double add(Double v1, Double v2, Double v3, Double v4, Double v5) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null)
			v2= new Double(0) ;
		if(v3==null)
			v3= new Double(0) ;
		if(v4==null)
			v4= new Double(0);
		if(v5==null)
			v5= new Double(0);
		return add(v1, v2, v3, v4, v5, 0, 5);
	}

	/**
	 * 功能说明：提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @param v3
	 *            加数
	 * @param v4
	 *            加数
	 * @param v5
	 *            加数
	 * @param v6
	 *            加数
	 * @return 六个参数的和
	 */
	public static final double add(double v1, double v2, double v3, double v4, double v5, double v6) {
		return add(v1, v2, v3, v4, v5, v6, 6);
	}

	/**
	 * 功能说明：提供精确的加法运算(此方法为底层调用方法)。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @param v3
	 *            加数
	 * @param v4
	 *            加数
	 * @param v5
	 *            加数
	 * @param v6
	 *            加数
	 * @param n
	 *            参数个数
	 * @return n个参数的和
	 */
	private static final double add(double v1, double v2, double v3, double v4, double v5, double v6, int n) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = null;
		BigDecimal b4 = null;
		BigDecimal b5 = null;
		BigDecimal b6 = null;
		switch (n) {
		case 2:
			return b1.add(b2).doubleValue();
		case 6:
			b6 = new BigDecimal(Double.toString(v6));
		case 5:
			b5 = new BigDecimal(Double.toString(v5));
		case 4:
			b4 = new BigDecimal(Double.toString(v4));
		case 3:
			b3 = new BigDecimal(Double.toString(v3));
		}
		switch (n) {
		case 3:
			return b1.add(b2).add(b3).doubleValue();
		case 4:
			return b1.add(b2).add(b3).add(b4).doubleValue();
		case 5:
			return b1.add(b2).add(b3).add(b4).add(b5).doubleValue();
		case 6:
			return b1.add(b2).add(b3).add(b4).add(b5).add(b6).doubleValue();
		default:
			return 0;
		}
	}
	/**
	 * 递归计算加法，所有为空的数据全部自动替换成0
	 * @param datas
	 * @return
	 */
	public static final Double addAll(Double ... datas) {
		if(datas == null||datas.length==0){
			return 0.0;
		}else if(datas.length == 1){
			return datas[0]==null?0.0:datas[0];
		}else {
			BigDecimal num = new BigDecimal(0);
			int size = datas.length;
			Double value = datas[0]==null?0.0:datas[0];
			datas[0] = datas[size-1];
			datas = Arrays.copyOf(datas, size-1);
			return num.add(new BigDecimal(value)).add(new BigDecimal(addAll(datas))).doubleValue();
		}
	}
	/**
	 * 递归计算加法，所有为空的数据全部自动替换成0
	 * @param datas
	 * @return
	 */
	public static final Double subAll(Double ... datas) {
		if(datas == null||datas.length==0){
			return 0.0;
		}else if(datas.length == 1){
			return datas[0]==null?0.0:datas[0];
		}else {
			BigDecimal num = new BigDecimal(0);
			int size = datas.length;
			Double value = datas[0]==null?0.0:datas[0];
			datas[0] = datas[size-1];
			datas = Arrays.copyOf(datas, size-1);
			return num.add(new BigDecimal(value)).subtract(new BigDecimal(addAll(datas))).doubleValue();
		}
	}
	/**
	 * 功能说明：提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static final double sub(double v1, double v2) {
		return add(v1, -v2, 0, 0, 0, 0, 2);
	}

	/**
	 * 功能说明：提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @param v3
	 *            减数
	 * @return 三个参数的差
	 */
	public static final double sub(double v1, double v2, double v3) {
		return add(v1, -v2, -v3, 0, 0, 0, 3);
	}

	/**
	 * 功能说明：提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @param v3
	 *            减数
	 * @param v4
	 *            减数
	 * @return 四个参数的差
	 */
	public static final double sub(double v1, double v2, double v3, double v4) {
		return add(v1, -v2, -v3, -v4, 0, 0, 4);
	}

	/**
	 * 功能说明：提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @param v3
	 *            减数
	 * @param v4
	 *            减数
	 * @param v5
	 *            减数
	 * @return 五个参数的差
	 */
	public static final double sub(double v1, double v2, double v3, double v4, double v5) {
		return add(v1, -v2, -v3, -v4, -v5, 0, 5);
	}

	/**
	 * 功能说明：提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @param v3
	 *            减数
	 * @param v4
	 *            减数
	 * @param v5
	 *            减数
	 * @param v6
	 *            减数
	 * @return 六个参数的差
	 */
	public static final double sub(double v1, double v2, double v3, double v4, double v5, double v6) {
		return add(v1, -v2, -v3, -v4, -v5, -v6, 6);
	}

	/**
	 * 提供精确的乘法运算.
	 */
	public static double mul(Double v1, Double v2) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null)
			v2= new Double(0) ;
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算，并对运算结果截位.
	 * 
	 * @param scale
	 *            运算结果小数后精确的位数
	 */
	public static double mul(Double v1, Double v2, int scale) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null)
			v2= new Double(0) ;
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算.
	 * 
	 * @see #div(double, double, int)
	 */
	public static Double div(Double v1, Double v2) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null || v2 ==0)
			return null;
		return div(v1, v2, 15);
	}
	
	public static Double div(Double v1, Integer v2) {
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null || v2 ==0)
			return null;
		return div(v1, v2, 15);
	}

	/**
	 * 提供（相对）精确的除法运算. 由scale参数指定精度，以后的数字四舍五入.
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null || v2 ==0)
			return null;
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static Double div(Integer v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(v1==null)
			v1= new Integer(0) ;
		if(v2==null || v2 ==0)
			return null;
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static Double div(Double v1, Integer v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(v1==null)
			v1= new Double(0) ;
		if(v2==null || v2 ==0)
			return null;
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理.
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将传入值减一，用于生成主键（适于生成负数当主键，如：资产分组主键）
	 * 
	 * @param n
	 *            主键初始值
	 * @return 下一个主键值
	 */
	public static int nextId(int n) {
		AtomicInteger uniqueId = new AtomicInteger(n);
		return uniqueId.decrementAndGet();
	}

	/**
	 * @description 考虑有些地方会用到多位相乘法，原方法采用多次调用二位数乘法，这里写一公用方法
	 *              适用场景：多位double数字相乘(采用累计相成方法)
	 * @param num
	 *            保留的小数位数
	 * @param dNum
	 *            待处理数据
	 * @return 多位相乘以后的结果 Double类型
	 */
	public static double mul(int num, Object... dNum) {

		BigDecimal fDecimal = new BigDecimal("0");// 初始化一个空参
		BigDecimal sDecimal = null;
		boolean firstCount = true;
		for (Object object : dNum) {
			if(object==null){
				object = new BigDecimal("0");
			}
			if (firstCount) {
				fDecimal = new BigDecimal(String.valueOf(object));// 对参数进行第一次赋值
				firstCount = false;
			} else {
				sDecimal = new BigDecimal(String.valueOf(object));
				fDecimal = fDecimal.multiply(sDecimal);// 记录累积值
				if(fDecimal.compareTo(new BigDecimal("0"))==0){
					return fDecimal.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
			}
		}
		double reDouble = fDecimal.doubleValue();
		return num == 0 ? reDouble : fDecimal.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 重构此方法不限制小数位数返回
	 * @param dNum
	 * @return
	 */
	public static double mul(Object... dNum) {
		return mul(0, dNum);
	}

	/**
	 * 字符型转换为双精度浮点型
	 * 
	 * @param val
	 * @return
	 */
	public static double parseDouble(String val) {
		if (StringUtil.isEmpty(val)) {
			val = "0";
		}
		BigDecimal fDecimal = new BigDecimal(val);
		return fDecimal.doubleValue();
	}

	/**
	 * 数字格式化
	 * 
	 * @param val
	 * @param format
	 *            取整：# 以百分比方式计数，并取两位小数：#.##% 保留两位小数，并千分位显示：##,###.00
	 * @return
	 */
	public static String format(double val, String format) {
		return new DecimalFormat(format).format(val);
	}

	/**
	 * 数字格式化 //注意不一定四舍五入，有时候（偶数后的5）舍5！alex20041124解决这个问题！ 相当于vb中的format，不过只能是数字
	 * 
	 * @param number
	 *            double类型的数字
	 * @param format
	 *            格式
	 * @return 格式化后的字符串
	 */
	public static final String formatNumber(double number, String format) {
		int decs = 0;// 要保留的小数位数

		if (format.trim().length() == 0)
			format = "#0.00";
		int i = format.indexOf(".");
		if (i >= 0) {
			for (decs = ++i; decs < format.length(); decs++) {
				if (format.charAt(decs) != '0' && format.charAt(decs) != '#')
					break;
			}
			decs -= i;
		}
		if (format.indexOf("%") >= 0)
			decs += 2;// 百分比

		return (new DecimalFormat(format)).format(round(number, decs));
	}

	/**
	 * 获取数字类型保留位数格式化工具
	 * 
	 * @param blws
	 *            保留位数
	 * @return
	 * @throws NumberFormatException
	 * @throws YssException
	 */
	public static final String getNumberFormat(int blws) {

		StringBuffer ws = new StringBuffer();
		try {
			ws.append("00");
			for (int i = 3; i <= blws; i++) {
				ws.append("0");
			}
		} catch (NumberFormatException e) {
			return "###0.00############";
		}
		return ("###0." + ws.toString());
	}

	/**
	 * 功能说明：移动加权计算卖出成本
	 * 
	 * @param map
	 *            : 持仓数据
	 * @param dSaleAmount
	 *            : 卖出数量
	 * @param iCb8ws
	 *            : 小数位数
	 * @exception YssException
	 * @return double
	 */
	public static double curOutPrice(double dKcMoney, double dKcAmount, double dSaleAmount, String iCb8ws) {

		double dSalecb = 0;
		if (dKcAmount > dSaleAmount) {
			if (iCb8ws.length() != 0 && !iCb8ws.equalsIgnoreCase("0")) {
				dSalecb = NumUtil.mul(NumUtil.round(NumUtil.div(dKcMoney, dKcAmount), Integer.parseInt(iCb8ws)),
						dSaleAmount, 2);
			} else {
				dSalecb = NumUtil.mul(NumUtil.div(dKcMoney, dKcAmount), dSaleAmount, 2);
			}
		} else {
			// 库存数量要么大于卖出数量，要么相等 相等时卖出成本＝库存金额
			dSalecb = dKcMoney;
		}
		return dSalecb;
	}

	/**
	 * 累积概率分布函数
	 * 
	 * @param number
	 * @return
	 */
	public static double probability(double number) {
		double y = Math.abs(number);
		double y2 = y * y;
		double z = Math.exp(-0.5 * y2) * 0.398942280401432678;
		double p = 0;
		int k = 28;
		double s = -1;
		double fj = k;
		if (y > 3) {
			// 当y>3时
			for (int i = 1; i <= k; i++) {
				p = fj / (y + p);
				fj = fj - 1.0;
			}
			p = z / (y + p);
		} else {
			// 当y<3时
			for (int i = 1; i <= k; i++) {
				p = fj * y2 / (2.0 * fj + 1.0 + s * p);
				s = -s;
				fj = fj - 1.0;
			}
			p = 0.5 - z * y / (1 - p);
		}
		if (number > 0)
			p = 1.0 - p;
		return p;
	}

	/**
	 * 功能说明：提供精确的比较大小方法
	 * 
	 * @param v1
	 * @param v2
	 * @return result >0 v1较大; =0 相等; <0 v2较大
	 */
	public static int compare(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		int result = b1.compareTo(b2);
		return result;
	}

	/**
	 * Object转Double
	 * @param obj
	 * @return
	 * @Description: object转double
	 * @author cdc dc.chen@wescxx.com 
	 * @date 2017年3月3日 下午5:04:44 
	 * 
	*/
	public static double ObjectToDouble(Object obj){
		if(obj == null){
			return 0;
		}
		if(obj instanceof Double){
			return (Double)obj;
		}
		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 *  科学计算法 转Double
	 */
	public static String parseDouble4(Double dou){
		if(dou == null){
			return null;
		}
		BigDecimal bd = new BigDecimal(dou); 
		return  bd.toPlainString();
	}
	
	/**
	 * 科学计数法转换
	 * @return
	 */
	public static String parseDouble4(Double dou,int blwx){
		String valueTemp="";
		BigDecimal bd = new BigDecimal(dou); 
		valueTemp = bd.toPlainString();
		String str="0.";
	    for(int i=0;i<blwx;i++){
	      str = str+"0";
	    }
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat(str); 
		valueTemp=  df.format(Double.parseDouble(valueTemp));
		return valueTemp;
	}
	
	/**
	 * 小数转成百分数
	 * @param dou  需要转换的小数
	 * @param num  保留小数点后几位
	 * @return
	 */
	public static String doubleToPercent(Double dou, int num) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(num);
		return nf.format(dou);
	}
	/**
	 * 讲一个数字强行转换为保留scal位的小数，
	 * 如果传入的num为空的话，直接当0。
	 * 如果scal为负数火null的话，scal则为0
	 * 例如,
	 * numFormatToString(1,2) -->1.00
	 * numFormatToString(1.11,0) -->1
	 * numFormatToString(null,2) -->0.00
	 * @param num  需要格式化的数字
	 * @param scal 保留的小数位 
	 * @author xinwei.huang
	 * @return
	 */
	public static String numFormatToString(Double num, Integer scal) {
		if (scal == null || scal < 0) {
			scal = 0;
		}
		StringBuilder result = new StringBuilder();
		if (num == null) {
			result.append("0");
			if (scal > 0) {
				result.append(".");
				while (scal > 0) {
					result.append("0");
					scal--;
				}
			}
		} else {
			String snum = parseDouble4(num,6);
			String[] strings = snum.split("\\.");
			if (strings.length > 1) {// 如果本来就带小数
				if(scal > 0){//如果保留小数位
					result.append(snum);
					int len = strings[1].length();
					if(len < scal){//继续添加
						while(len < scal){
							result.append("0");
							scal--;
						}
					}else if(len > scal){//删除
						while(len > scal){
							result.deleteCharAt(result.length()-1);
							scal++;
						}
					}
				} else{ //不保留小数位
					result.append(strings[0]);
				}
			}else {// 没有小数位
				result.append(num);
				if (scal > 0) {// 但是需要有小数位
					result.append(".");
					while (scal > 0) {
						result.append("0");
						scal--;
					}
				}
			}
		}
		return result.toString();
	}

	
}