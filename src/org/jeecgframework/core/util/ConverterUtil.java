package org.jeecgframework.core.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Table;

import jeecg.system.pojo.base.TSType;
import jeecg.system.pojo.base.TSTypegroup;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.Distinct;
import org.jeecgframework.core.annotation.PrimaryKeyPolicy;
import org.jeecgframework.core.annotation.excel.Excel;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.compartor.commonCompartor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hippo.nky.common.constants.Constants;
import com.hippo.nky.entity.common.impl.PageCommonEntity;

/**
 * 转换工具类
 * 
 * @author XuDL
 */
public final class ConverterUtil {
	/** 数字. */
	public static final String DATA_TYPE_NUMBER = "U";

	/** 文字. */
	public static final String DATA_TYPE_CHAR = "C";

	/** 补全类别：SAME. */
	public static final String COMPLEMENT_SAME = "SAME";

	/** 分隔符：元素 */
	public static final String SEPARATOR_ELEMENT = "#EM#";

	/** 分隔符：key-value */
	public static final String SEPARATOR_KEY_VALUE = "#KV#";
	
	/** Excel导出单元格属性：title */
	public static final String EXCEL_TITLE = "title";
	
	/** Excel导出单元格属性：width */
	public static final String EXCEL_WIDTH = "width";
	
	/** Excel导出单元格属性：color */
	public static final String EXCEL_COLOR = "color";
	
	/** Excel导出单元格属性：align */
	public static final String EXCEL_ALIGN = "align";
	
	/** Excel导出单元格宽度：最小值 */
	public static final int EXCEL_WIDTH_MIN = 10;
	
	/** Excel导出单元格宽度：最大值 */
	public static final int EXCEL_WIDTH_MAX = 50;
	
	/** Excel导出单元格WIDTH：auto */
	public static final String EXCEL_WIDTH_AUTO = "auto";

	/** 时间格式：日期型(yyyy/MM/dd) */
	public static final String FORMATE_DATE = "yyyy/MM/dd";

	/** 时间格式：日期时间24小时制型(yyyy/MM/dd HH:mm:ss) */
	public static final String FORMATE_DATE_TIME_24H = "yyyy/MM/dd HH:mm:ss";

	/** 时间格式：日期时间12小时制型(yyyy/MM/dd hh:mm:ss) */
	public static final String FORMATE_DATE_TIME_12H = "yyyy/MM/dd hh:mm:ss";

	/** 时间格式：时间戳24小时制型(yyyy/MM/dd HH:mm:ss.SSS) */
	public static final String FORMATE_TIME_STAMP_24H = "yyyy/MM/dd HH:mm:ss.SSS";

	/** 时间格式：时间戳12小时制型(yyyy/MM/dd hh:mm:ss.SSS) */
	public static final String FORMATE_TIME_STAMP_12H = "yyyy/MM/dd hh:mm:ss.SSS";
	
	/** 正则日期类型:中线 */
	public static final String REGEX_DATE_MIDDELLINE = "^\\d{4}-(0?[1-9]|[1][012])-(0?[1-9]|[12][0-9]|[3][01])$";
	
	/** 正则日期类型:反斜线 */
	public static final String REGEX_DATE_BACKSLASH = "^\\d{4}/(0?[1-9]|[1][012])/(0?[1-9]|[12][0-9]|[3][01])$";
	
	/** 正则日期时间类型:中线 */
	public static final String REGEX_DATE_TIME_MIDDELLINE = "^\\d{4}-(0?[1-9]|[1][012])-(0?[1-9]|[12][0-9]|[3][01])[\\s]+\\d([0-1][0-9]|2?[0-3]):([0-5][0-9]):([0-5][0-9])$";
	
	/** 正则日期时间类型:反斜线 */
	public static final String REGEX_DATE_TIME_BACKSLASH = "^\\d{4}/(0?[1-9]|[1][012])/(0?[1-9]|[12][0-9]|[3][01])[\\s]+\\d([0-1][0-9]|2?[0-3]):([0-5][0-9]):([0-5][0-9])$";

	/** 正则时间戳类型:中线 */
	public static final String REGEX_TIME_STAMP_MIDDELLINE = "^\\d{4}-(0?[1-9]|[1][012])-(0?[1-9]|[12][0-9]|[3][01])[\\s]+\\d([0-1][0-9]|2?[0-3]):([0-5][0-9]):([0-5][0-9])\\.\\d{3}$";
	
	/** 正则时间戳类型:反斜线 */
	public static final String REGEX_TIME_STAMP_BACKSLASH = "^\\d{4}/(0?[1-9]|[1][012])/(0?[1-9]|[12][0-9]|[3][01])[\\s]+\\d([0-1][0-9]|2?[0-3]):([0-5][0-9]):([0-5][0-9])\\.\\d{3}$";
	
	/** 方法名:get */
	public static final String METHOD_GET = "get";
	
	/** 方法名:set */
	public static final String METHOD_SET = "set";


	/**
	 * 构造函数
	 */
	private ConverterUtil() {
	}

	/**
	 * Date->yyyy/MM/dd HH:mm型的String转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param dt
	 *            日期
	 * @return String数值
	 * 
	 */
	public static String toDateTimeString(Date dt) {
		if (dt == null) {
			return null;
		}

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		return df.format(dt);
	}

	/**
	 * Object->Timestamp型的String转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return String数值
	 * 
	 */
	public static String toTimestampString(Object obj) {
		if (obj == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSSSSS");

		if (obj instanceof java.util.Date) {
			return df.format(new Timestamp(((java.util.Date) obj).getTime()));
		}
		if (obj instanceof Timestamp) {
			return df.format((Timestamp) obj);
		}
		return obj.toString();
	}
	
	/**
	 * Long->Date转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param date
	 *            date
	 * @return Date数值
	 */
	public static Date toDate(Long date) {
		if (date == null) {
			return null;
		}
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(date);
		return cl.getTime();
	}

	/**
	 * Object->Date转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return Date数值
	 */
	public static Date toDate(Object obj, String formate) {
		if ((obj == null) || ("".equals(obj))) {
			return null;
		}
		if (obj instanceof Long) {
			return toDate(toLong(obj));
		}
		if (obj instanceof Timestamp) {
			return (Timestamp) obj;
		}
		if (obj instanceof Date) {
			return new Timestamp(((Date) obj).getTime());
		}
		if (obj instanceof String) {
			String temp = obj.toString();
			if (temp.length() == 8) {
				char[] arra = temp.toCharArray();
				String temp1 = new String(arra, 0, 4);
				String temp2 = new String(arra, 4, 2);
				String temp3 = new String(arra, 6, 2);
				obj = temp1 + "/" + temp2 + "/" + temp3;
			}
		}
		DateFormat df = new SimpleDateFormat(formate);
		Date dateTemp = null;
		try {
			dateTemp = df.parse(obj.toString());
		} catch (ParseException e) {
			throw new RuntimeException("日期不合法", e);
		}
		return new Timestamp(dateTemp.getTime());

	}

	/**
	 * Object->Timestamp转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return Timestamp数值
	 * 
	 */
	public static Timestamp toTimestamp(Object obj) {
		Date date = toDate(obj, "yyyy/MM/dd");
		return date == null ? null : new Timestamp(date.getTime());
	}

	/**
	 * Object->Integer转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return Integer数值
	 */
	public static Integer toInteger(Object obj) {
		return toInteger(obj, null);
	}

	/**
	 * Object->Integer转换
	 * <p>
	 * 入参是null时，返回值为nullValue
	 * 
	 * @param obj
	 *            Object
	 * @return Integer数值
	 */
	public static Integer toInteger(Object obj, Integer nullValue) {
		if (obj instanceof Double) {
			return ((Double) obj).intValue();
		}
		return obj == null ? nullValue : Integer.valueOf(obj.toString());
	}

	/**
	 * Object->BigDecimal转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return BigDecimal数值
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		return toBigDecimal(obj, null);
	}

	/**
	 * Object->BigDecimal转换
	 * <p>
	 * 入参是null时，返回值为nullValue
	 * 
	 * @param obj
	 *            Object
	 * @return BigDecimal数值
	 */
	public static BigDecimal toBigDecimal(Object obj, BigDecimal nullValue) {
		if ((obj == null) || (obj.equals(""))) {
			return nullValue;
		}
		return new BigDecimal(obj.toString());
	}

	/**
	 * Object->String转换
	 * <p>
	 * 入参是null时，返回值为""
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 * 
	 */
	public static String toNotNullString(Object obj) {
		return obj == null ? "" : toString(obj);
	}

	/**
	 * Object->String转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 * 
	 */
	public static String toString(Object obj) {
		if (null == obj || "".equals(obj)) {
			return null;
		}
		if (obj instanceof String) {
			return obj.toString();
		}
		if (obj instanceof Integer) {
			return Integer.toString(toInteger(obj));
		}
		if (obj instanceof Double) {
			return Double.toString(toDouble(obj));
		}
		if (obj instanceof Long) {
			return Long.toString(toLong(obj));
		}
		if (obj instanceof BigDecimal) {
			return toBigDecimal(obj).toPlainString();
		}
		if (obj instanceof Timestamp) {
			return dateToString((Date) obj, FORMATE_TIME_STAMP_24H);
		}
		if (obj instanceof Date) {
			return dateToString((Date) obj, FORMATE_DATE_TIME_24H);
		}
		if (obj instanceof Object[]) {
			Object[] objArray = (Object[]) obj;
			return String.valueOf(objArray[0]);
		}
		return String.valueOf(obj);
	}
	
	/**
	 * Object->Boolean转换
	 * <p>
	 * flase<String> --> false<boolean>
	 * TRUE<String> --> true<boolean>
	 * 0<String> --> false<boolean>
	 * 1<String> --> true<boolean>
	 * @param Obj
	 * @return
	 */
	public static Boolean toBoolean(Object Obj){
		if(Obj instanceof Boolean){
			return ((Boolean) Obj).booleanValue();
		}
		String boo = toNotNullString(Obj);
		if("true".equalsIgnoreCase(boo)){
			return true;
		}
		if("false".equalsIgnoreCase(boo)){
			return false;
		}
		if("0".equalsIgnoreCase(boo)){
			return false;
		}
		if("1".equalsIgnoreCase(boo)){
			return true;
		}
		return false;
	}

	// /**
	// * Timestamp转字符串
	// *
	// * @param ts
	// * Timestamp
	// * @param format
	// * 转换格式
	// * @return 字符串
	// */
	// public static String timestampToString(Timestamp ts, String format) {
	// SimpleDateFormat sf = new SimpleDateFormat(format);
	// Calendar cl = Calendar.getInstance();
	// cl.setTimeInMillis(ts.getTime());
	// return sf.format(cl.getTime());
	// }

	/**
	 * 日期->字符串转换
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            转换格式
	 * @return 字符串
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
	
	/**
	 * 字符串->日期转换
	 * <p>
	 * 支持yyyy/MM/dd|yyyy/MM/dd HH:mm.ss|yyyy/MM/dd HH:mm.SSS|
	 * yyyy-MM-dd|yyyy-MM-dd HH:mm.ss|yyyy-MM-dd HH:mm.SSS
	 * 的转换。如果不在这六种格式之内，返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 字符串
	 * @throws ParseException
	 */
	public static Date toDate(String str) throws ParseException {
		// 设置日期正则格式
		Map<String, String> regexMap = new HashMap<String, String>();
		regexMap.put("REGEX_DATE_MIDDELLINE", REGEX_DATE_MIDDELLINE);
		regexMap.put("REGEX_DATE_BACKSLASH", REGEX_DATE_BACKSLASH);
		regexMap.put("REGEX_DATE_TIME_MIDDELLINE", REGEX_DATE_TIME_MIDDELLINE);
		regexMap.put("REGEX_DATE_TIME_BACKSLASH", REGEX_DATE_TIME_BACKSLASH);
		regexMap.put("REGEX_TIME_STAMP_MIDDELLINE", REGEX_TIME_STAMP_MIDDELLINE);
		regexMap.put("REGEX_TIME_STAMP_BACKSLASH", REGEX_TIME_STAMP_BACKSLASH);

		regexMap = new HashMap<String, String>();
		SimpleDateFormat sf = null;
		for (String regex : regexMap.keySet()) {
			Pattern pat = Pattern.compile(regex);
			Matcher mat = pat.matcher(str);
			if (mat.find()) {
				if (regex.contains("TIME_STAMP")) {
					sf = new SimpleDateFormat(FORMATE_TIME_STAMP_24H);
				} else if (regex.contains("DATE_TIME")) {
					sf = new SimpleDateFormat(FORMATE_DATE_TIME_24H);
				} else if (regex.contains("DATE")) {
					sf = new SimpleDateFormat(FORMATE_DATE);
				}
				break;
			}
		}
		if (null == sf) {
			return null;
		}
		return sf.parse(str);
	}

	/**
	 * 
	 * Object->Double转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return Double型数值
	 * 
	 */
	public static Double toDouble(Object obj) {
		return toDouble(obj, null);
	}

	/**
	 * 
	 * Object->Double转换
	 * <p>
	 * 入参是null时，返回值为nullValue
	 * 
	 * @param obj
	 *            Object
	 * @return Double型数值
	 * 
	 */
	public static Double toDouble(Object obj, Double nullValue) {
		if ((obj == null) || (obj.equals(""))) {
			return nullValue;
		}
		return new Double(obj.toString());
	}
	
	/**
	 * 
	 * Object->Long转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param obj
	 *            Object
	 * @return Long型数值
	 * 
	 */
	public static Long toLong(Object obj) {
		return toLong(obj, null);
	}
	
	/**
	 * 
	 * Object->Long转换
	 * <p>
	 * 入参是null时，返回值为nullValue
	 * 
	 * @param obj
	 *            Object
	 * @return Long型数值
	 * 
	 */
	public static Long toLong(Object obj, Long nullValue) {
		if ((obj == null) || (obj.equals(""))) {
			return nullValue;
		}
		if(obj instanceof Long){
			return Long.valueOf(obj.toString());
		}
		if(obj instanceof Double){
			return toDouble(obj).longValue();
		}
		if(obj instanceof BigDecimal){
			return toBigDecimal(obj).longValue();
		}
		return new Long(obj.toString());
	}

	/**
	 * 
	 * double型数值小数部分舍除
	 * <p>
	 * 四舍五入
	 * 
	 * @param value
	 *            double型数值
	 * @param scale
	 *            小数点以后位数
	 * @return double型数值
	 */
	public static Double getRoundValue(Double value, int scale) {
		double result = 0.0;
		if (null != value) {
			result = new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.HALF_UP).doubleValue();
		}
		return result;
	}

	/**
	 * double型数值小数部分舍除
	 * <p>
	 * 进位
	 * 
	 * @param value
	 *            double型数值
	 * @param scale
	 *            小数点以后位数
	 * @return double型数值
	 */
	public static Double getRoundUpValue(Double value, int scale) {
		double result = 0.0;
		if (null != value) {
			result = new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.UP).doubleValue();
		}
		return result;
	}

	/**
	 * double型数值小数部分舍除
	 * <p>
	 * 直接舍掉
	 * <p>
	 * 
	 * @param value
	 *            double型数值
	 * @param scale
	 *            小数点以后位数
	 * @return double型数值
	 */
	public static Double getRoundDownValue(Double value, int scale) {
		double result = 0.0;
		if (null != value) {
			result = new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.DOWN).doubleValue();
		}
		return result;
	}

	/**
	 * double型数值小数部分舍除
	 * <p>
	 * round: 0:直接舍掉 1：四舍五入 2：进位
	 * 
	 * @param dbValue
	 *            double型数值
	 * @param round
	 *            舍值方式
	 * @param scale
	 *            小数点以后位数
	 * @return double型数值
	 */
	public static double getDoubleValueWithScale(double dbValue, int round, int scale) {
		// 0:直接舍掉 1：四舍五入 2：进位
		if (round == 0) {
			dbValue = getRoundDownValue(dbValue, scale);
		} else if (round == 1) {
			dbValue = getRoundValue(dbValue, scale);
		} else if (round == 2) {
			dbValue = getRoundUpValue(dbValue, scale);
		}
		return dbValue;
	}

	/**
	 * 字符串按照固定长度填充
	 * <p>
	 * 文字型：后补空格 数字型：前补0
	 * 
	 * @param obj
	 *            字符串
	 * @param objType
	 *            变换类型
	 * @param objLen
	 *            长度
	 * @return 变换后的字符串
	 */
	public static String convertBySize(Object obj, String objType, int objLen) {
		String ret = null;
		int oldSize = 0;
		int size = objLen;

		if (obj == null || size == 0) {
			ret = "";
		} else {
			ret = obj.toString();
			oldSize = obj.toString().getBytes().length;
		}
		if (DATA_TYPE_NUMBER.equals(objType)) {
			// 无符号
			if (oldSize < size) {
				int m = size - oldSize;
				for (int i = 0; i < m; i++) {
					// 在最前面增加0
					ret = "0" + ret;
				}
			} else {
				ret = ret.substring(oldSize - size);
			}
		} else if (DATA_TYPE_CHAR.equals(objType)) {
			// 文字型
			if (oldSize < size) {
				int m = size - oldSize;
				for (int i = 0; i < m; i++) {
					// 最后半角空格增加
					ret = ret + " ";
				}
			} else if (oldSize > size) {
				byte[] bytes = ret.getBytes();
				byte[] retBytes = new byte[size];
				for (int i = 0; i < size; i++) {
					retBytes[i] = bytes[i];
				}
				ret = new String(retBytes);
				if (ret.getBytes().length != size) {
					ret = ret + " ";
				}
			}
		}
		return ret;
	}

	/**
	 * 有符号数字转->Integer转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param signNumber
	 *            有符号的字符串
	 * @return Integer型数值
	 */
	public static Integer convSignNumStrToInteger(String signNumber) {
		if (signNumber == null || "".equals(signNumber)) {
			return null;
		}
		try {
			if (signNumber.substring(0, 1).equals("+")) {
				return new Integer(signNumber.substring(1));
			} else if (signNumber.substring(0, 1).equals("-")) {
				return new Integer(Integer.parseInt(signNumber.substring(1)) * -1);
			} else {
				return new Integer(signNumber);
			}
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * 有符号数字转->BigDecimal转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param signNumber
	 *            有符号的字符串
	 * @return BigDecimal型数值
	 */
	public static BigDecimal convSignNumStrToBigDecimal(String signNumber) {
		if (signNumber == null || "".equals(signNumber)) {
			return null;
		}
		try {
			if (signNumber.substring(0, 1).equals("+")) {
				return new BigDecimal(signNumber.substring(1));
			} else if (signNumber.substring(0, 1).equals("-")) {
				return new BigDecimal(signNumber.substring(1)).negate();
			} else {
				return new BigDecimal(signNumber);
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * BigDecimal->Integer转换(小数部分舍掉)
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param bdNum
	 *            BigDecimal型数值
	 * @return Integer数值
	 */
	public static Integer convBDNumToInteger(BigDecimal bdNum) {
		if (bdNum == null) {
			return null;
		}
		return new Integer(bdNum.setScale(0, RoundingMode.DOWN).intValue());
	}

	/**
	 * 有符号字符串->Integer转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param str
	 *            String文字列
	 * @return Integer数值
	 */
	public static Integer convStrToInteger(String str) {
		if (isEmpty(str)) {
			return null;
		}
		try {
			if (str.substring(0, 1).equals("+")) {
				return new Integer(str.substring(1));
			} else if (str.substring(0, 1).equals("-")) {
				return new Integer(Integer.parseInt(str.substring(1)) * -1);
			} else {
				return new Integer(str);
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * String->BigDecimal转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param str
	 *            String文字列
	 * @return BigDecimal数值
	 */
	public static BigDecimal convStrToBigDecimal(String str) {
		if (isEmpty(str)) {
			return null;
		}
		try {
			if (str.substring(0, 1).equals("+")) {
				return new BigDecimal(str.substring(1));
			} else if (str.substring(0, 1).equals("-")) {
				return new BigDecimal(str.substring(1)).negate();
			} else {
				return new BigDecimal(str);
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 16进制String->Integer转换
	 * <p>
	 * 入参是null时，返回值为null
	 * 
	 * @param str
	 *            String文字列
	 * @return Integer数值
	 */
	public static Integer convStrToIntegerHex(String str) {
		if (isEmpty(str)) {
			return null;
		}
		try {
			return new Integer(Integer.parseInt(str, 16));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * properties数组型数据按逗号分隔
	 * 
	 * @param properties
	 *            字符串
	 * 
	 * @return List
	 */
	public static List<String> getSplitProperties(String properties) {
		List<String> arrayList = new ArrayList<String>();
		if (isNotEmpty(properties)) {
			String propertieLine = properties.replace("[", "").replace("]", "");
			for (String property : propertieLine.split(",")) {
				arrayList.add(property);
			}
		}
		return arrayList;
	}

	/**
	 * 字符串按特定符号进行分割，返回分割后不为空的字符的List
	 * 
	 * @param line
	 *            字符串
	 * @param mark
	 *            分隔符
	 * @return List
	 */
	public static List<String> getSplitList(String line, String mark) {
		List<String> arrayList = new ArrayList<String>();
		if (isNotEmpty(line)) {
			for (String word : line.split(mark)) {
				if (isNotEmpty(word)) {
					arrayList.add(word);
				}
			}
		}
		return arrayList;
	}

	/**
	 * 字符串按特定符号进行分割，返回分割后不为空的字符的数组
	 * 
	 * @param line
	 *            字符串
	 * @param mark
	 *            分隔符
	 * @return List
	 */
	public static String[] getSplitArray(String line, String mark) {
		List<String> arrayList = new ArrayList<String>();
		if (isNotEmpty(line)) {
			for (String word : line.split(mark)) {
				if (isNotEmpty(word)) {
					arrayList.add(word);
				}
			}
		}
		String[] temp = new String[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			temp[i] = arrayList.get(i);
		}
		return temp;
	}

	/**
	 * 根据自动补全List
	 * 
	 * @param list
	 *            List
	 * @param length
	 *            长度
	 * @param obj
	 *            补全的Object
	 * @return List
	 */
	public static <T> List<T> complementSize(List<T> list, int length, T obj) {
		if (list.size() <= 0) {
			return list;
		}
		// 补全类别是same时，等于list的第一个
		if (COMPLEMENT_SAME.equals(obj)) {
			obj = list.get(0);
		}
		// 如果长度小于，则增加；大于则从右侧删除多余的项
		if (list.size() < length) {
			for (int i = 0; i <= length - list.size(); i++) {
				list.add(obj);
			}
		} else if (list.size() > length) {
			list.subList(0, (list.size() - length - 1));
		}
		return list;
	}

	/**
	 * 系统路径->相对路径转换
	 * <p>
	 * 【\】->【/】
	 * 
	 * @param sysPath
	 *            系统路径
	 * @return 相对路径
	 */
	public static String sysPathToPath(String sysPath) {
		sysPath = sysPath.replace("\\", "/");
		return sysPath;
	}

	/**
	 * 相对路径->系统路径转换
	 * <p>
	 * 【/】->【\】
	 * 
	 * @param path
	 *            相对路径
	 * @return 系统路径
	 */
	public static String pathToSysPath(String path) {
		path = path.replace("/", "\\");
		return path;
	}
	
	/**
	 * 取得系统发布后的完整路径
	 * <p>
	 * 系统发布后名字有可能会发生空白或者填写的情况，该方法能后将发布路径和URL进行拼接
	 * 
	 * @param contextPath 发布路径
	 * @param url 链接
	 * @return 当前URl所属的完整链接
	 */
	public static String getActionPath(String contextPath, String url){
		if(!contextPath.startsWith("/")){
			contextPath = "/" + contextPath;
		}
		if(url.startsWith("/")){
			if("/".equals(contextPath)){
				return url;
			}
			// 没有指定工程名的情况
			return contextPath + url;
		} else {
			if("/".equals(contextPath)){
				return "/" + url;
			}
			// 指定了工程名的情况
			return contextPath + "/" + url;
		}
	}

	/**
	 * 取得查询条件需要替换的字典list(以","分隔的字符串)
	 * 
	 * @param list
	 *            字典list
	 * @return 以","分隔的字符串
	 */
	public static String getQueryReplaceList(List<TSType> list) {
		String returnString = "";
		for (TSType ts : list) {
			returnString += ts.getTypename() + "_" + ts.getTypecode() + ",";
		}
		returnString = returnString.substring(0, returnString.length() - 1);
		return returnString;
	}

	/**
	 * 把list中的bean和T相同属性的value复制到T中，返回T的list
	 * 
	 * @param lst
	 *            list
	 * @param bean
	 *            T的class
	 * @return T的list
	 * @throws Exception
	 *             异常
	 */
	public static <T> List<T> copyListBeanToListBean(List<?> lst, Class<T> bean, String... notCopyAttr) throws Exception {
		List<T> retLst = new ArrayList<T>();
		for (int i = 0; i < lst.size(); i++) {
			T temp = bean.newInstance();
			MyBeanUtils.copyBean2Bean(temp, lst.get(i));
			retLst.add(temp);
		}
		if (null != notCopyAttr) {
			for (T entity : retLst) {
				for (String attr : notCopyAttr) {
					Field field =entity.getClass().getField(attr);
					Class<?> clazz = field.getType();
					// 获得首字母大写的方法名的set方法
					String methodName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
					Method method = entity.getClass().getMethod(methodName, clazz);
					method.invoke(entity);
				}
			}
		}
		return retLst;
	}

	/**
	 * 设置list中bean的属性
	 * <p>
	 * Properties的格式如下<br/>
	 * Map<String, List<Object>>:{"属性名"，[类别，值]}
	 * 
	 * @param lst
	 *            list
	 * @param setProperties
	 *            需要设置的属性
	 * 
	 * @throws NoSuchMethodException
	 *             没有找到set方法异常
	 * @throws SecurityException
	 *             安全异常
	 * @throws InvocationTargetException
	 *             反射set方法异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 * @throws IllegalArgumentException
	 *             反射方法参数异常
	 */
	public static <T> void setListBeanValue(List<T> lst, Map<String, List<Object>> setProperties)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		for (String key : setProperties.keySet()) {
			List<Object> proLst = setProperties.get(key);
			// 获得首字母大写的方法名的set方法
			String methodName = "set" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
			for (T temp : lst) {
				Method method = temp.getClass().getMethod(methodName, proLst.get(0).getClass());
				method.invoke(temp, proLst.get(1));
			}
		}
	}

	/**
	 * 取得Entity对应的Db2Page数组
	 * <p>
	 * dictionaryMap格式如下：<br/>
	 * {"字段名","字典名"}<br/>
	 * 如果Map中含有customDict，则认为改字典是自定义字典
	 * 
	 * @param clazz
	 *            Entity的class
	 * @param dictionaryMap
	 *            entity对应的数据字典的字段名
	 * @return Db2Page数组
	 * @throws SecurityException
	 *             安全异常
	 * @throws NoSuchMethodException
	 *             没有找到get方法异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 * @throws InstantiationException
	 *             反射异常
	 */
	@SuppressWarnings("unchecked")
	public static Db2Page[] autoGetEntityToPage(Class<?> clazz, Map<String, Object> dictionaryMap)
			throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		Field[] fields = clazz.getDeclaredFields();
		List<Db2Page> db2PageList = new ArrayList<Db2Page>();
		// Db2Page[] db = new Db2Page[fields.length];
		Integer index = 0;
		for (Field field : fields) {
			// 获得首字母大写的方法名的get方法
			String methodName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			Method method = clazz.getMethod(methodName);
			Column col = method.getAnnotation(Column.class);
			if (null == col) {
				continue;
			}
			Object dictionary = dictionaryMap.get(field.getName());
			if (null != dictionary) {
				if (dictionaryMap.get(field.getName()) instanceof Map){
					db2PageList.add(new Db2Page(field.getName(), col.name(), null, (Map<String, Object>) dictionary));
				} else {
					db2PageList.add(new Db2Page(field.getName(), col.name(), null, toNotNullString(dictionary)));
				}
			} else {
					db2PageList.add(new Db2Page(field.getName(), col.name(), null));
			}
			index++;
		}
		return db2PageList.toArray(new Db2Page[1]);
	}

	/**
	 * 取得Entity对应的Db2Page数组
	 * <p>
	 * dictionaryMap格式如下：<br/>
	 * {"字段名","字典名"}<br/>
	 * 如果Map中含有customDict，则认为改字典是自定义字典
	 * @param map
	 *            数据map
	 * @param dictionaryMap
	 *            entity对应的数据字典的字段名
	 * @return Db2Page数组
	 * @throws SecurityException
	 *             安全异常
	 * @throws NoSuchMethodException
	 *             没有找到get方法异常
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Db2Page[] autoGetMapToPage(Map<String, Object> map, Map<String, Object> dictionaryMap)
			throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		List<Db2Page> db2PageList = new ArrayList<Db2Page>();
		// Db2Page[] db = new Db2Page[map.size()];
		Integer index = 0;
		for (String key : map.keySet()) {
			String dictionaryName = toString(dictionaryMap.get(key));
			if (null != dictionaryName) {
				db2PageList.add(new Db2Page(key, key, null, dictionaryName));
			} else {
				if(toBoolean(dictionaryMap.get("customDict"))){
					db2PageList.add(new Db2Page(key, key, null, dictionaryMap));
				} else {
					db2PageList.add(new Db2Page(key, key, null));
				}
			}
			index++;
		}
		return db2PageList.toArray(new Db2Page[1]);
	}

	/**
	 * 取得数据字典中值所对应的文字
	 * 
	 * @param dictionaryName
	 *            字典名称
	 * @param objValue
	 *            值
	 * @return 文字
	 */
	public static String getDictionaryText(String dictionaryName, Object objValue) {
		List<TSType> types = TSTypegroup.allTypes.get(dictionaryName.toLowerCase());
		for (TSType ts : types) {
			if (objValue.equals(ts.getTypecode())) {
				return ts.getTypename();
			}
		}
		return null;
	}
	
	/**
	 * 取得数据字典
	 * 
	 * @param dictionaryName
	 *            字典名称
	 * @return 文字
	 */
	public static List<TSType> getDictionary(String dictionaryName) {
		return TSTypegroup.allTypes.get(dictionaryName.toLowerCase());
	}
	
	/**
	 * 取得数据字典名
	 * 
	 * @param dictionaryName
	 *            字典名称
	 * @param code
	 *            code值
	 * @return 字典名
	 */
	public static String getDictionaryName(String dictionaryName, String code) {
		if(StringUtils.isNotEmpty(code)) {
			List<TSType> dList = TSTypegroup.allTypes.get(dictionaryName.toLowerCase());
			for (TSType tsType : dList) {
				if (code.equals(tsType.getTypecode())) {
					return tsType.getTypename();
				}
			}
		}
		return "";
	}

	/**
	 * 取得数据字典code
	 * 
	 * @param dictionaryName
	 *            字典名称
	 * @param name
	 *            文字
	 * @return 字典code
	 */
	public static String getDictionaryCode(String dictionaryName, String name) {
		List<TSType> dList = TSTypegroup.allTypes.get(dictionaryName.toLowerCase());
		for (TSType tsType : dList) {
			if (name.equals(tsType.getTypename())) {
				return tsType.getTypecode();
			}
		}
		return "";
	}

	/**
	 * 取得data的查询条件
	 * 
	 * @param entity
	 *            实体
	 * @param notQueryAttr
	 *            不进行查询的字段
	 * @return sql条件字符串
	 * @throws IllegalArgumentException
	 *             反射方法参数异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 * @throws InvocationTargetException
	 *             反射get方法异常
	 * @throws SecurityException
	 *             安全异常
	 * @throws NoSuchMethodException
	 *             没有找到get方法异常
	 */
	public static <T> String getDataGridQuerySql(T entity, String... notQueryAttr) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		String queryStr = "";
		Map<String, String> attrMap = new HashMap<String, String>();
		for (String attr : notQueryAttr) {
			attrMap.put(attr, attr);
		}
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (isNotEmpty(attrMap.get(field.getName()))) {
				continue;
			}
			// 获得首字母大写的方法名的get方法
			String methodName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			Method method = entity.getClass().getMethod(methodName);
			Object value = method.invoke(entity);
			if (null != value && !"".equals(value)) {
				if (value instanceof String) {
					queryStr += " and " + field.getName() + " like '%" + value + "%'";
				}
				if (value instanceof Date) {
					String date = DateUtils.format((Date) value, "yyyy-MM-dd HH:mm:sss");
					queryStr += " and " + field.getName() + "=to_date('" + date + "','YYYY-MM-DD HH24:MI:SSS')";
				}
				if (value instanceof Integer) {
					queryStr += " and " + field.getName() + "=" + value;
				}
				if (value instanceof BigDecimal) {
					queryStr += " and " + field.getName() + "=" + value;
				}
				if (value instanceof Long) {
					queryStr += " and " + field.getName() + "=" + value;
				}
				if (value instanceof Double) {
					queryStr += " and " + field.getName() + "=" + value;
				}
			}
		}
		return queryStr;
	}

	/**
	 * 判断是否是空字符串 null和"" 都返回 true
	 * 
	 * @author Robin Chang
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * Map转换String
	 * 
	 * @param map
	 *            需要转换的Map
	 * @return String转换后的字符串
	 */
	public static String mapToString(Map<String, Object> map) {
		return mapToString(map, SEPARATOR_ELEMENT, SEPARATOR_KEY_VALUE);
	}

	/**
	 * 
	 * Map转换String
	 * 
	 * @param map
	 *            需要转换的Map
	 * @return String转换后的字符串
	 */
	public static String mapToString(Map<String, Object> map, String elementSeparator, String keySeparator) {
		StringBuffer stb = new StringBuffer();
		// 遍历map
		for (String key : map.keySet()) {
			if (isEmpty(key)) {
				continue;
			}
			Object value = map.get(key);
			stb.append(key + keySeparator + toNotNullString(value));
			stb.append(elementSeparator);
		}
		return stb.toString();
	}

	/**
	 * 
	 * String转换Map
	 * 
	 * @param mapText
	 *            需要转换的字符串
	 * @return Map
	 */
	public static Map<String, Object> stringToMap(String mapText) {
		return stringToMap(mapText, SEPARATOR_ELEMENT, SEPARATOR_KEY_VALUE);
	}

	/**
	 * 
	 * String转换Map
	 * 
	 * @param mapText
	 *            需要转换的字符串
	 * @param elementSeparator
	 *            字符串中每个元素的分割
	 * @param keySeparator
	 *            字符串中的分隔符每一个key与value中的分割
	 * @return Map
	 */
	public static Map<String, Object> stringToMap(String mapText, String elementSeparator, String keySeparator) {
		if (isEmpty(mapText)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 转换为数组
		String[] text = mapText.split(elementSeparator);
		for (String str : text) {
			if (isEmpty(str)) {
				continue;
			}
			// 转换key与value的数组
			String[] keyText = str.split(keySeparator);
			if (keyText.length < 2) {
				continue;
			}
			String key = keyText[0];
			String value = keyText[1];
			map.put(key, value);
		}
		return map;

	}
	
	/**
	 * 
	 * String转换Map(key与value相同)
	 * 
	 * @param mapText
	 *            需要转换的字符串
	 * @param elementSeparator
	 *            字符串中每个元素的分割
	 * @return Map
	 */
	public static Map<String, Object> stringToMap(String mapText, String elementSeparator) {
		if (isEmpty(mapText)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 转换为数组
		String[] text = mapText.split(elementSeparator);
		for (String str : text) {
			if (isEmpty(str)) {
				continue;
			}
			// key与value相同
			String key = str;
			String value = str;
			map.put(key, value);
		}
		return map;

	}

	/**
	 * String转换List
	 * 
	 * @param listText
	 *            需要转换的文本
	 * @param separator
	 *            分隔符
	 * 
	 * @return List<String>
	 */

	public static List<String> stringToList(String listText) {
		return stringToList(listText, SEPARATOR_ELEMENT);
	}

	/**
	 * String转换List
	 * 
	 * @param listText
	 *            需要转换的文本
	 * @param separator
	 *            分隔符
	 * @return List<String>
	 */
	public static List<String> stringToList(String listText, String separator) {
		if (isEmpty(listText)) {
			return  new ArrayList<String>();
		}
		List<String> list = new ArrayList<String>();
		String[] text = listText.split(separator);
		for (String str : text) {
			if (isEmpty(str)) {
				continue;
			}
			list.add(str);
		}
		return list;
	}

	/**
	 * List转换String
	 * 
	 * @param list
	 *            需要转换的List
	 * @param separator
	 *            分隔符
	 * @return String
	 */
	public static String listToString(List<?> list, String separator) {
		StringBuffer stb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				String str = toNotNullString(obj);
				if (isEmpty(str)) {
					continue;
				}
				stb.append(str);
				stb.append(separator);
			}
		}
		return stb.toString();
	}

	/**
	 * String转换链接参数
	 * 
	 * @param str
	 *            字符串
	 * @param elementSeparator
	 *            字符串中每个元素的分割
	 * @param keySeparator
	 *            字符串中的分隔符每一个key与value中的分割
	 * @return
	 */
	public static String stringToLinkparams(String str) {
		return stringToLinkparams(str, SEPARATOR_ELEMENT, SEPARATOR_KEY_VALUE);
	}

	/**
	 * String转换链接参数
	 * 
	 * @param str
	 *            字符串
	 * @param elementSeparator
	 *            字符串中每个元素的分割
	 * @param keySeparator
	 *            字符串中的分隔符每一个key与value中的分割
	 * @return
	 */
	public static String stringToLinkparams(String str, String elementSeparator, String keySeparator) {
		if (isEmpty(str)) {
			return null;
		}
		StringBuffer stb = new StringBuffer();
		// 转换为数组
		String[] elements = str.split(elementSeparator);
		for (String element : elements) {
			if (isEmpty(element)) {
				continue;
			}
			// 转换key与value的数组
			String[] keyText = element.split(keySeparator);
			if (keyText.length < 2) {
				continue;
			}
			stb.append("&" + keyText[0]);
			stb.append("=" + keyText[1]);
		}
		return stb.toString();
	}

	/**
	 * entity中属性值转成json格式
	 * 
	 * @param obj
	 *            entity
	 * 
	 * @throws NoSuchMethodException
	 *             没有找到set方法异常
	 * @throws SecurityException
	 *             安全异常
	 * @throws InvocationTargetException
	 *             反射set方法异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 * @throws IllegalArgumentException
	 *             反射方法参数异常
	 */
	public static <T> void priseMarkForEntity(T obj) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 获得首字母大写的方法名的get方法
			String methodName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			Method method = obj.getClass().getMethod(methodName);
			Column col = method.getAnnotation(Column.class);
			if (null != col) {
				Object value = method.invoke(obj);
				if (value instanceof String) {
					value = string2Json(value.toString());
					// 获得首字母大写的方法名的set方法
					String setMethodName = "set" + Character.toUpperCase(field.getName().charAt(0))
							+ field.getName().substring(1);
					Method setMethod = obj.getClass().getMethod(setMethodName, field.getType());
					setMethod.invoke(obj, value);
				}
			}
		}
	}

	/**
	 * 字符串中特殊字符替换成JSON格式
	 * 
	 * @param str
	 *            字符串
	 * @return 转换后的字符串
	 */
	public static String string2Json(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
				case '\"' :
					sb.append("\\\"");
					break;
				case '\\' :
					sb.append("\\\\");
					break;
				case '/' :
					sb.append("\\/");
					break;
				case '\b' :
					sb.append("\\b");
					break;
				case '\f' :
					sb.append("\\f");
					break;
				case '\n' :
					sb.append("\\n");
					break;
				case '\r' :
					sb.append("\\r");
					break;
				case '\t' :
					sb.append("\\t");
					break;
				default :
					sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 通过表名取得Entity
	 * 
	 * @param physicalName
	 *            表物理名
	 * @return 表对应的Entity
	 * @throws ClassNotFoundException
	 *             未找到类异常
	 * @throws InstantiationException
	 *             反射异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 */
	public static Object getEntityWithTableName(String physicalName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		// 取得entity文件夹内的所有文件
		File file = new File(ResourceUtil.getClassPath() + Constants.CLASS_PATH_ENTITY);
		// 取得文件名
		String[] fileNames = file.list();
		for (String fileName : fileNames) {
			// 判断后缀为class的文件
			if (fileName.endsWith(Constants.SUFFIX_CLASS)) {
				Class<?> clazz = Class.forName(Constants.PACKAGE_ENTITY + fileName.replace(Constants.SUFFIX_CLASS, ""));
				// 取得DB映射
				Table clazzAnn = (Table) clazz.getAnnotation(Table.class);
				if (null != clazzAnn) {
					// 如果与物理名相同则返回实体类
					if (clazzAnn.name().toUpperCase().equals(physicalName.toUpperCase())) {
						return clazz.newInstance();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 将Entity的属性和值转成Map格式
	 * 
	 * @param entity
	 *            实体类
	 * @return
	 * @throws SecurityException
	 *             安全异常
	 * @throws NoSuchMethodException
	 *             没有找到get方法异常
	 * @throws IllegalArgumentException
	 *             反射方法参数异常
	 * @throws IllegalAccessException
	 *             反射实体类异常
	 * @throws InvocationTargetException
	 *             反射get方法异常
	 */
	public static Map<String, Object> entityToMap(Object entity)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Field[] fileds = entity.getClass().getDeclaredFields();
		for (Field field : fileds) {
			// 获得get方法名
			String methodName = "get"
					+ Character.toUpperCase(field.getName().charAt(0))
					+ field.getName().substring(1);
			if(isNotEmpty(methodName)){
				Method getMethod = entity.getClass().getMethod(methodName);
				retMap.put(field.getName(), getMethod.invoke(entity));
			}
		}
		return retMap;
	}

	/**
	 * 取得entity相关的所有map集合
	 * <p>
	 * 1.excelSetMethodMap:excel列和entity对应的set方法map;</br>
	 * 2.excelGetMethodMap:excel列和entity对应的get方法map;</br>
	 * 3.excelTypeMap:excel列和entity属性类型对应的map;</br>
	 * 4.fieldTypeMap:entity中属性和类型的map;</br>
	 * 5.distinctFieldList:entity中需要去重的属性的List。</br>
	 * 6.excelSortAttributesList:包含excel列的属性进行排序的List
	 * <p>
	 * 
	 * @param entity
	 *            实体类
	 * @param attrMethod
	 *            get/set
	 * @return map
	 * @throws NoSuchMethodException
	 *             没有找到get方法异常
	 * @throws SecurityException
	 *             安全异常
	 */
	public static Map<String, Object> entityToMapForAll(Object entity) throws SecurityException, NoSuchMethodException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, String> excelSetMethodMap = new HashMap<String, String>();
		Map<String, String> excelGetMethodMap = new HashMap<String, String>();
		Map<String, Object> excelTypeMap = new HashMap<String, Object>();
		Map<String, Object> fieldTypeMap = new HashMap<String, Object>();
		List<String> distinctFieldList = new ArrayList<String>();
		List<Excel> excelSortAttributesList = new ArrayList<Excel>();
		Field[] fileds = entity.getClass().getDeclaredFields();
		for (Field field : fileds) {
			// 获得首字母大写的方法名
			String method = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
			Excel annt = field.getAnnotation(Excel.class);
			if (null != annt) {
				// set方法名
				String setMethodName = "set" + method;
				excelSetMethodMap.put(annt.exportName(), setMethodName);

				// get方法名
				String getMethodName = "get" + method;
				excelGetMethodMap.put(annt.exportName(), getMethodName);

				excelTypeMap.put(annt.exportName(), field.getType());
				excelSortAttributesList.add(annt);
			}
			fieldTypeMap.put(field.getName(), field.getType());
			// get方法名
			String getMethodName = "get" + method;
			Method getMethod = entity.getClass().getMethod(getMethodName);
			Distinct distinct = getMethod.getAnnotation(Distinct.class);
			if(null != distinct){
				distinctFieldList.add(field.getName());
			}
		}
		retMap.put("excelSetMethodMap", excelSetMethodMap);
		retMap.put("excelGetMethodMap", excelGetMethodMap);
		retMap.put("excelTypeMap", excelTypeMap);
		retMap.put("fieldTypeMap", fieldTypeMap);
		retMap.put("distinctFieldList", distinctFieldList);
		Collections.sort(excelSortAttributesList, new commonCompartor());
		retMap.put("excelSortAttributesList", excelSortAttributesList);
		return retMap;
	}
	
	/**
	 * 取得entity中属性和类型的map
	 * Key：value->属性名：属性类型
	 * 
	 * @param entity 实体类
	 * @return map
	 */
	public static Map<String, Object> getFieldTypeMap(Object entity) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Field[] fileds = entity.getClass().getDeclaredFields();
		for (Field field : fileds) {
			retMap.put(field.getName(), field.getType());
		}
		return retMap;
	}

	/**
	 * 取得excel和entity对应的map
	 * Key：value->excel列名：entity中属性的方法名
	 * <p>
	 * attrMethod指定取得get还是set方法
	 * 
	 * @param entity 实体类
	 * @param attrMethod get/set
	 * @return map
	 */
	public static Map<String, String> getExcelSetOrGetMethodMap(Object entity, String attrMethod) {
		Map<String, String> retMap = new HashMap<String, String>();
		Field[] fileds = entity.getClass().getDeclaredFields();
		for (Field field : fileds) {
			Excel annt = field.getAnnotation(Excel.class);
			if (null != annt) {
				// 获得首字母大写的方法名的attrMethod方法
				String methodName = attrMethod + Character.toUpperCase(field.getName().charAt(0))
						+ field.getName().substring(1);
				retMap.put(annt.exportName(), methodName);
			}
		}
		return retMap;
	}
	
	/**
	 * 取得entity对应的excel列与entity类型的的map
	 * Key：value->excel列名：entity中属性的类型
	 * 
	 * @param entity 实体类
	 * @return map
	 */
	public static Map<String, Object> getExcelTypeMap(Object entity) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Field[] fileds = entity.getClass().getDeclaredFields();
		for (Field field : fileds) {
			Excel annt = field.getAnnotation(Excel.class);
			if (null != annt) {
				retMap.put(annt.exportName(), field.getType());
			}
		}
		return retMap;
	}
	
	/**
	 * 取得去重SQL
	 * 
	 * @param tableName 表名
	 * @param distinctFieldList 去重的list
	 * @return 去重sql
	 */
	public static String getDistinctSql(String tableName, List<String> distinctFieldList) {
		String sql = "DELETE FROM " + tableName + " WHERE ROWID NOT IN (SELECT MAX(ROWID) FROM " + tableName
				+ " group by ";
		for (String field : distinctFieldList) {
			sql += (field + ",");
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		return sql;
	}
	
	/**
	 * 获取当前时间
	 * @param format
	 * @return
	 */
	public static String getCurrentTime (String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		java.util.Date currTime = new java.util.Date();

		String curTime = formatter.format(currTime);
		return curTime;
	}
	
	/**
	 * 获取年度列表
	 * @param num 跨度（前后）
	 * @return
	 */
	public static List<String> getYearList(int num){
		List<String> yearList = new ArrayList<String>();
		Integer year = Integer.parseInt(getCurrentTime("yyyy"));
		int begain = year - num + 1;
		//int end = year + num;
		for (int i = begain; i <= year; i ++  ) {
			yearList.add(String.valueOf(i));
		}
		return yearList;
		
	}
	
	/**
	 * 
	 * String转换Options
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param defaultVal
	 *            默认值
	 * @param elementSeparator
	 *            字符串中每个元素的分割
	 * @param keySeparator
	 *            字符串中的分隔符每一个key与value中的分割
	 * @return Map
	 */
	public static String formatOptions(String str, String defaultVal) {
		return formatOptions(str, defaultVal, SEPARATOR_ELEMENT, SEPARATOR_KEY_VALUE);
	}

	/**
	 * 
	 * String转换Options
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param defaultVal
	 *            默认值
	 * @param elementSeparator
	 *            字符串中每个元素的分割
	 * @param keySeparator
	 *            字符串中的分隔符每一个key与value中的分割
	 * @return Map
	 */
	public static String formatOptions(String str, String defaultVal, String elementSeparator, String keySeparator) {
		StringBuffer sf = new StringBuffer();
		String[] elems = str.split(elementSeparator);
		// 拼接下拉框
		if (isEmpty(defaultVal) || "undefined".equals(defaultVal)) {
			sf.append("<option value=\"\" selected=\"selected\">");
		}
		for (String elem : elems) {
			String[] keyValue = elem.split(keySeparator);
			if (keyValue.length < 2) {
				continue;
			}
			String key = keyValue[0];
			String value = keyValue[1];
			if (isNotEmpty(key) && key.equals(defaultVal)) {
				sf.append("<option value=\"" + key
						+ "\" selected=\"selected\">");
			} else {
				sf.append("<option value=\"" + key + "\">");
			}
			if (isNotEmpty(value)) {
				sf.append(value);
				sf.append("</option>");
			}

		}
		return sf.toString();
	}
	
	/**
	 * 设置主键策略
	 * 
	 * @param entity 实体
	 */
	public static <T> void setPrimaryKeyPolicy(T entity, CommonServiceImpl serivce){
		List<T> list = new ArrayList<T>();
		list.add(entity);
		setPrimaryKeyPolicyList(list, serivce);
	}
	
	/**
	 * 设置主键策略
	 * 
	 * @param entitys 实体List
	 */
	public static <T> void setPrimaryKeyPolicyList(List<T> entitys, CommonServiceImpl serivce) {
		try{
			
		if (null == entitys || entitys.isEmpty()) {
			return;
		}
		// 取得getId方法
		Method getIdMethod = entitys.get(0).getClass().getDeclaredMethod("getId");
		// 取得GenericGenerator
		GenericGenerator genericGenerator = getIdMethod.getAnnotation(GenericGenerator.class);
		// 取得PrimaryKeyPolicy
		PrimaryKeyPolicy policy = getIdMethod.getAnnotation(PrimaryKeyPolicy.class);
		String policyMethod = "";
		String policyType = "";
		// 如果entity的策略是手动才执行
		if (null == genericGenerator || genericGenerator.strategy().equals("assigned")) {
			if (null == policy) {
				return;
			}
			policyType = policy.policy();
			policyMethod = policy.method();
		} else {
			return;
		}
		// 判断策略方法 default:默认(method=default时，生成方式采用JAVA的UUID) oralce:ORACLE(method=oralce时,生成方式采用ORACLE的sys_guid())
		if (policyMethod.equalsIgnoreCase("default")) {
			for (T entity : entitys) {
				// 判断策略类型 auto自动 semi半自动
				if (policyType.equalsIgnoreCase("semi")) {
					String id = toString(getIdMethod.invoke(entity));
					if (isEmpty(id)) {
						setIdMethod(entity, getUUID());
					}
				} else if (policyType.equalsIgnoreCase("auto")) {
					setIdMethod(entity, getUUID());
				}
			}
		} else if (policyMethod.equalsIgnoreCase("oracle")) {
			List<String> idLst = serivce.getGUID(entitys.size());
			for (int i = 0; i < entitys.size(); i++) {
				if (policyType.equalsIgnoreCase("semi")) {
					String id = toString(getIdMethod.invoke(entitys.get(i)));
					if (isEmpty(id)) {
						setIdMethod(entitys.get(i), idLst.get(i));
					}
				} else if (policyType.equalsIgnoreCase("auto")) {
					setIdMethod(entitys.get(i), idLst.get(i));
				}
			}
		} else {
			if (policyMethod.contains(".")) {
				return;
			}
			String customClassName = policyMethod.substring(0, policyMethod.lastIndexOf("."));
			String customMethodName = policyMethod.substring(policyMethod.lastIndexOf(".") + 1);
			Class<?> customClass = Class.forName(customClassName);
			if (null != customClass) {
				Method customMethod = customClass.getDeclaredMethod(customMethodName);
				for (T entity : entitys) {
					if (policyType.equalsIgnoreCase("semi")) {
						String id = toString(getIdMethod.invoke(entity));
						if (isEmpty(id)) {
							setIdMethod(entity, toString(customMethod.invoke(customClass.newInstance())));
						}
					} else if (policyType.equalsIgnoreCase("auto")) {
						setIdMethod(entity, toString(customMethod.invoke(customClass.newInstance())));
					}
				}
			}
		}
		}catch(Exception e){
			return;
		}
		return;
	}
	
	/**
	 * 设置Id方法
	 * 
	 * @param entity 实体
	 * @param value 值
	 * @throws SecurityException 安全异常
	 * @throws NoSuchMethodException 未找到set方法异常
	 * @throws IllegalArgumentException 反射方法参数异常
	 * @throws IllegalAccessException 反射实体类异常
	 * @throws InvocationTargetException 反射set方法异常
	 */
	private static <T> void setIdMethod(T entity, String value) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// 取得setId方法
		Method setIdMethod = entity.getClass().getDeclaredMethod("setId", String.class);
		setIdMethod.invoke(entity, value);
	}
	
	/**
	 * 取得UUID
	 * 
	 * @return UUID
	 */
	public static String getUUID(){
		return toString(UUID.randomUUID()).replace("-", "");
	}

	/**
	 * Object -> Workbook
	 * 
	 * 对象->Excel工作簿
	 * 
	 * @param exportFileName
	 *            导出文件名
	 * @param list
	 *            对象List
	 * @return
	 * @throws Exception
	 */
	public static <T> List<Workbook> objectToWorkBook(String exportFileName,
			List<T> list) throws Exception {
		if((null != list && list.size() > 0)){
			if (list.get(0) instanceof Map) {
				return mapToWorkBook(exportFileName, list);
			} else {
				return entityToWorkBook(exportFileName, list);
			}
		}
		return new ArrayList<Workbook>();
	}
	
	/**
	 * Object -> Workbook
	 * 
	 * 对象->Excel工作簿
	 * 
	 * @param exportFileName
	 *            导出文件名
	 * @param list
	 *            对象List
	 * @return
	 * @throws Exception
	 */
	public static <T> List<Workbook> objectToWordFile(String exportFileName,
			List<T> list) throws Exception {
		if((null != list && list.size() > 0)){
			if (list.get(0) instanceof Map) {
				return mapToWorkBook(exportFileName, list);
			} else {
				return entityToWorkBook(exportFileName, list);
			}
		}
		return new ArrayList<Workbook>();
	}

	/**
	 * 
	 * mapList->Excel的List<br/>
	 * List<T extends Map> -> List<Excel extends Workbook>
	 * (默认03版本的Excel)
	 * 
	 * @param exportFileName
	 *            导出文件名
	 * @param entityList
	 *            mapList
	 * @return Excel的List
	 * @throws Exception
	 *             各种转换异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Workbook> mapToWorkBook(String exportFileName, List<T> mapList) throws Exception {
		List<Workbook> bookList = new ArrayList<Workbook>();
		// 取得配置文件中导入每次导出的最大数据行
		Integer exportMaxLine = ConverterUtil.toInteger(ResourceUtil.getConfigByName(Constants.EXPORT_MAX_LINE));
		
		// 抬头list
		List<Map<String, Object>> titMapList = new ArrayList<Map<String,Object>>();
		List<T> removeTitleList = new ArrayList<T>();
		for (T obj : mapList) {
			Map<String, Object> titColMap = (Map<String, Object>) obj;
			// 只有title设值width才有效，默认数据不能加width，所以带有width的即为title行
			for(String titleKey:titColMap.keySet()){
				if(null != stringToMap(toString(titColMap.get(titleKey))).get("width")){
					titMapList.add((Map<String, Object>) obj);
					removeTitleList.add(obj);
					break;
				}
			}
		}
		
		if(titMapList.size() < 1){
			// 取得title行 Map格式默认第一行是title
			Map<String, Object> columnsAttrMap = (Map<String, Object>) mapList.get(0);
			titMapList.add(columnsAttrMap);
			mapList.remove(0);
		} else {
			for(T obj:removeTitleList){
				mapList.remove(obj);
			}
		}
		
		// 取得BookNum
		int bookNum = getBookNum(mapList);
		
		// 循环生成book
		for (int i = 0; i < bookNum; i++) {
			String bookName = "";
			if(i == 0){
				// 第一个book的名=导入文件名
				bookName = exportFileName;
			} else {
				// 第N个book的名=导入文件名(N)
				bookName = exportFileName + "(" + (bookList.get(i - 1).getNumberOfSheets() + 1) + ")";
			}
			int subStart = i * exportMaxLine;
			int subEnd = (i * exportMaxLine) + exportMaxLine;
			// map的第一行是title
			if (subEnd > mapList.size()) {
				subEnd = mapList.size();
			}
			// 截取一个book数量的list
			List<T> subList = mapList.subList(subStart, subEnd);
			// 添加创建好的book
			bookList.add(createWorBookForMap(bookName, titMapList, (List<Map<String, Object>>) subList));
		}

		return bookList;
	}

	/**
	 * 取得bookNum
	 * 
	 * @param list List<T>
	 * @return bookNum
	 */
	private static <T> int getBookNum(List<T> list) {
		// 取得配置文件中导入每次导出的最大数据行
		Integer exportMaxLine = ConverterUtil.toInteger(ResourceUtil.getConfigByName(Constants.EXPORT_MAX_LINE));

		int bookNum = 0;
		int listSize = list.size();

		if (listSize % exportMaxLine != 0) {
			// 如果条数/最大导出有余数 则导出文件数应该=商+1
			bookNum = (listSize / exportMaxLine) + 1;
		} else {
			bookNum = listSize / exportMaxLine;
		}
		return bookNum;
	}
	
	/**
	 * 
	 * 实体List->Excel的List<br/>
	 * List<T extends Entity> -> List<Excel extends Workbook>
	 * (默认03版本的Excel)
	 * 
	 * @param exportFileName
	 *            导出文件名
	 * @param entityList
	 *            实体List
	 * @return Excel的List
	 * @throws Exception
	 *             各种转换异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Workbook> entityToWorkBook(String exportFileName, List<T> entityList) throws Exception {
		List<Workbook> bookList = new ArrayList<Workbook>();

		// 取得配置文件中导入每次导出的最大数据行
		Integer exportMaxLine = ConverterUtil.toInteger(ResourceUtil.getConfigByName(Constants.EXPORT_MAX_LINE));

		T entityInfo = entityList.get(0);
		// 取得entity所有相关参数
		Map<String, Object> entityAllMap = entityToMapForAll(entityInfo);
		// 取得excel和entity对应的map
		Map<String, String> excelGetMethodMap = (Map<String, String>) entityAllMap.get("excelGetMethodMap");
		// 取得entity对应的excel列与entity类型的的map
		Map<String, Object> excelTypeMap = (Map<String, Object>) entityAllMap.get("excelTypeMap");
		// 包含excel列的属性进行排序的List
		List<Excel> excelList = (List<Excel>) entityAllMap.get("excelSortAttributesList");

		// 取得BookNum
		int bookNum = getBookNum(entityList);
		
		// 循环生成book
		for (int i = 0; i < bookNum; i++) {
			String bookName = "";
			if(i == 0){
				// 第一个book的名=导入文件名
				bookName = exportFileName;
			} else {
				// 第N个book的名=导入文件名(N)
				bookName = exportFileName + "(" + (bookList.get(i - 1).getNumberOfSheets() + 1) + ")";
			}
			int subStart = i * exportMaxLine;
			int subEnd = (i * exportMaxLine) + exportMaxLine;
			if (subEnd > entityList.size()) {
				subEnd = entityList.size();
			}
			// 截取一个book数量的list
			List<T> subList = entityList.subList(subStart, subEnd);
			// 添加创建好的book
			bookList.add(createWorBookForEntity(bookName, excelTypeMap, excelGetMethodMap, excelList, subList));
		}
		
		return bookList;
	}
	
	/**
	 * 创建一个Excelbook
	 * 
	 * @param exportFileName
	 *            导入的文件名
	 * @param titMapList
	 *            导出列抬头list
	 * @param bodyList
	 *            bodyList
	 * @return
	 * @throws Exception
	 *             异常
	 */
	private static <T> Workbook createWorBookForMap(String exportFileName, List<Map<String, Object>> titMapList,
			List<Map<String, Object>> bodyList) throws Exception {
		// 创建工作簿
		Workbook workBook = ExcelUtils.createWorkBook();
		// 创建sheet
		Sheet workSheet = ExcelUtils.createSheet(workBook, exportFileName);
		// 创建整个sheet
		ExcelUtils.setSheetValues(workBook, workSheet, titMapList, bodyList);
		
		// // 创建抬头
		// ExcelUtils.createTitle(workSheet, titMapList,
		// ExcelUtils.getDefaultCellStyle(workBook));
		// // 创建数据行
		// ExcelUtils.createBody(workSheet, mapList);

		return workBook;
	}
	
	/**
	 * 创建一个Excelbook
	 * 
	 * @param exportFileName 导入的文件名
	 * @param excelTypeMap 导出名与数据类型的Map
	 * @param excelGetMethodMap 导出名与对应属性的get方法名的Map
	 * @param excelList entity中对应的Excel Annotation
	 * @param entityList 实体类List
	 * @return
	 * @throws Exception 异常
	 */
	private static <T> Workbook createWorBookForEntity(String exportFileName,
			Map<String, Object> excelTypeMap, Map<String, String> excelGetMethodMap,
			List<Excel> excelList, List<T> entityList) throws Exception {
		// 创建工作簿
		Workbook workBook = ExcelUtils.createWorkBook();
		// 创建sheet
		Sheet workSheet = ExcelUtils.createSheet(workBook, exportFileName);

		// 创建抬头
		ExcelUtils.createTitle(workSheet, excelList, ExcelUtils.getDefaultCellStyle(workBook), workBook);

		// 创建数据行
		ExcelUtils.createBody(workBook, workSheet, excelGetMethodMap, excelList, entityList);

		return workBook;
	}

	/**
	 * requestMap<?,?> -> Map<String, Object>
	 * <p>
	 * 不支持多个参数用一个key来取得,如果同一个key取得了一个String[]，<br/>
	 * 转换后的值为String[]中最后一个value的值
	 * <p>
	 * @param requestMap request参数Map
	 * @return
	 */
	public static Map<String, Object> requestParamsToMap(Map<? , ?> requestMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for(Object key:requestMap.keySet()){
			Object value = requestMap.get(key);
			if(null == value || "".equals(value)){
				continue;
			} else if(value instanceof String[]){
				for(String val :(String[])value){
					if(isNotEmpty(val)){
						resultMap.put(toString(key), val);
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 设置翻页信息到entity
	 * 
	 * @param dataGrid
	 * @param entity
	 */
	public static void setPageNavigateInfo(DataGrid dataGrid, PageCommonEntity entity) {
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		int beginIndex = (page - 1) * rows;
		int endIndex = beginIndex + rows;
		entity.setBeginIndex(beginIndex);
		entity.setEndIndex(endIndex);
	}

	/***
	 * 
	 * 从自定义的方法中取得Excel的数据
	 * <p>
	 * 1.默认只能调用service的方法</br> 
	 * 2.入参必须是Map如果不写，service中可以用ContextHolderUtils.getSession()和getRequest()来取得上下文对象</br> 
	 * 3.返回值必须是List<T></br>
	 * 
	 * @param customService
	 *            自定义service
	 * @param paramMap
	 *            入参Map
	 * @return 取得的Excel的数据
	 * @throws Exception
	 *             反射时产生的各种异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getDataFormCustomService(String customService,
			Map<String, Object> paramMap) throws Exception {
		
		// 按【.】分隔
		String[] customAttr = customService.split("\\.");

		if (customAttr.length < 3) {
			return null;
		}
		
		// 拆分取得包名、类名、方法名
		String pageName = customAttr[0];
		String className = customAttr[1];
		String methodName = customAttr[2];
		
		if (isEmpty(className)) {
			return null;
		}
		// 包名
		String pgName = "com.hippo.nky.service.impl." + pageName + ".";
		// class名
		String clName = "";
		
		// 如果接口的service是以[I]结尾则删除
		if(className.endsWith("I")){
			className = className.substring(0, className.length() - 1);
		}
		if (!className.endsWith("Impl")) {
			clName = className + "Impl";
		} 
		// class全名 默认只能调用service的方法
		clName = pgName + clName;

		// 反射类
		Class<?> customClass = Class.forName(clName);
		if (null == customClass) {
			return null;
		}
		Service serAnn = customClass.getAnnotation(Service.class);
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(ContextHolderUtils.getSession().getServletContext());
		Object customObj = wac.getBean(serAnn.value());
		
		// 入参必须是Map
		Method customMethod = null;
		// 返回值必须是List<T>
		Object result = null;

		if(null == paramMap){
			customMethod = customClass.getDeclaredMethod(methodName);
			result = customMethod.invoke(AopTargetUtils.getTarget(customObj));
		} else {
			customMethod = customClass.getDeclaredMethod(methodName, Map.class);
			result = customMethod.invoke(AopTargetUtils.getTarget(customObj), paramMap);
		}
				
		if (!(result instanceof List)) {
			return null;
		}
		return (List<T>) result;
	}
	
	 /**
     * 取得Object(实体Bean)所有的属性与值的键值对(K,V)
     * <p>
     * 属性必须要有get方法
     * 
     * @param entity
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> getAllFileds(Object entity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> result = new HashMap<String, Object>();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getFields();
        // 取得属性(定义public与继承的属性)
        for (Field field : fields) {
            Except annExcept = field.getAnnotation(Except.class);
            if(null == annExcept){
                String getMethod = converToMethodName(field.getName(), METHOD_GET);
                Method method;
                try {
                    method = clazz.getMethod(getMethod);
                } catch (NoSuchMethodException e) {
                    method = null;
                }
                if (ConverterUtil.isNotEmpty(method)) {
                    Object value = method.invoke(entity);
                    // 如果属性值不为空则放到返回值中
                    if(ConverterUtil.isNotEmpty(value)){
                        result.put(field.getName(), value);
                    }
                }
            }
        }
        // 取得属性(定义的所有属性包含public)
        Field[] fields2 = clazz.getDeclaredFields();
        for (Field field : fields2) {
            Except annExcept = field.getAnnotation(Except.class);
            if(null == annExcept){
                String getMethod = converToMethodName(field.getName(), METHOD_GET);
                // 过滤出getFields没有取得的
                if (!result.containsKey(getMethod)) {
                    Method method;
                    try {
                        method = clazz.getMethod(getMethod);
                    } catch (NoSuchMethodException e) {
                        method = null;
                    }
                    if (ConverterUtil.isNotEmpty(method)) {
                        // 如果是私有属性不设置该权限会报错
                        method.setAccessible(true);
                        Object value = method.invoke(entity);
                        // 如果属性值不为空则放到返回值中
                        if(ConverterUtil.isNotEmpty(value)){
                            result.put(field.getName(), value);
                        }
                    }
                }
            }
        }
        return result;
    }
    
    
    /**
	 * 取得JAVA属性反射方法名
	 * 
	 * @param attr
	 *            属性
	 * @param method
	 *            get/set/other/""
	 * @return 方法名
	 */
	public static String converToMethodName(String attr, String method) {
		String methodName = "";
		if (isEmpty(attr)) {
			return methodName;
		}
		if (attr.length() >= 2) {
			char first = attr.charAt(0);
			char second = attr.charAt(1);
			// mm-> Mm
			if (Character.isLowerCase(first) && Character.isLowerCase(second)) {
				return method + Character.toUpperCase(first) + attr.substring(1);
			} else {
				// MM -> MM
				// mM -> mM
				// Mm -> Mm
				return method + attr;
			}
		} else {
			return method + Character.toUpperCase(attr.charAt(0)) + attr.substring(1);
		}
	}


}