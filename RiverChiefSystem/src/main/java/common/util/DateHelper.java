package common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @TODO 日期转换帮助类
 * @author 李玮勤
 * @created 2017-4-12 下午20:59:02
 * @version 1.0.0
 * @sinace 1.0.0
 * @modify
 */
public class DateHelper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("日期相加=>" + formatDate(addDateByDay(new Date(), 4)));
		System.out.println("返回两个年月数相差月份数==>" + DateResult("20170401", new Date(), "yyyyMMdd"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		Date d2 = new Date();
		try {
			d1 = sdf.parse("2017-04-08 10:10:10");
			d2 = sdf.parse("2017-04-12 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("使用 daysOfTwo 相差天数 = " + daysOfTwo(d1, d2));
	}

	/**
	 * @TODO 日期转换帮助类
	 * @author 李玮勤
	 * @created 2017-4-12 下午20:59:02
	 * @version 1.0.0
	 * @sinace 1.0.0
	 * @modify
	 */
	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(java.util.Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatTimestamp(java.util.Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		}
		return result;
	}

	/**
	 * 以指定的格式来转换字符串并格式化为日期
	 * 
	 * @param dateStr
	 *            String
	 * @param format
	 *            String
	 * @return String
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			if ((!dateStr.equals("")) && (dateStr.length() < format.length())) {
				dateStr += format.substring(dateStr.length()).replaceAll("[YyMmDdHhSs]", "0");
			}
			date = (java.util.Date) df.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 以指定的格式来转换字符串并格式化为时间戳
	 * 
	 * @param dateStr
	 *            String
	 * @param format
	 *            String
	 * @return String
	 */
	public static java.sql.Timestamp parseTimestamp(String dateStr, String format) {
		java.util.Date date = parseDate(dateStr, format);
		if (date != null) {
			long t = date.getTime();
			return new java.sql.Timestamp(t);
		} else
			return null;
	}

	/**
	 * 以指定的格式来转换字符串并格式化为时间戳格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 */
	public static java.sql.Timestamp parseTimestamp(String dateStr) {
		return parseTimestamp(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 以指定的格式来转换字符串并格式化为时间戳格式为"yyyy-MM-dd"
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 */
	public static java.sql.Timestamp parseTimesDate(String dateStr) {
		return parseTimestamp(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDateByDay(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param hour
	 *            小时
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDateByHour(java.util.Date date, int hour) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) hour) * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param minute
	 *            分钟
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDateByMinute(java.util.Date date, int minute) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) minute) * 60 * 1000);
		return c.getTime();
	}

	/**
	 * 月份数加上传参的相加数
	 * 
	 * @param strDate
	 *            String
	 * @param number
	 *            int
	 * @return 返回相加后的年月
	 */
	public static String getMonthDate(String strDate, int number) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(strDate.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(strDate.substring(5, 7)));
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, number);
		Date date = cal.getTime();
		return df.format(date).substring(0, 7);
	}

	/**
	 * 给年-月的时间类型加上日期，日期为每月头一天
	 * 
	 * @param strDate
	 *            String
	 * @return 返回相加后的年月日
	 */
	public static Date getFirstDay(String strDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String strMonthDate = strDate + "-01";

		try {
			return df.parse(strMonthDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 给年-月的时间类型加上一个月
	 * 
	 * @param strDate
	 *            String
	 * @return 返回相加后的年月
	 */
	public static String getNextMonth(String strDate) {
		int month = Integer.parseInt(strDate.substring(5, 7));
		int year = Integer.parseInt(strDate.substring(0, 4));
		String strMonth = "";
		String strYear = "";
		if (month == 12) {
			strMonth = "01";
			strYear = Integer.toString(year + 1);
		} else {
			strMonth = Integer.toString(month + 1);
			if (strMonth.length() == 1) {
				strMonth = "0" + strMonth;
				strYear = Integer.toString(year);
			}
		}

		return strYear + "-" + strMonth;
	}
	
	/**
	 * 给年-月的时间类型前一个月
	 * 
	 * @param strDate
	 *            String
	 * @return 返回相加后的年月
	 */
	public static String getPreviousMonth(String strDate) {
		int month = Integer.parseInt(strDate.substring(5, 7));
		int year = Integer.parseInt(strDate.substring(0, 4));
		String strMonth = "";
		String strYear = "";
		if (month == 01) {
			strMonth = "12";
			strYear = Integer.toString(year - 1);
		} else {
			strMonth = Integer.toString(month - 1);
			if (strMonth.length() == 1) {
				strMonth = "0" + strMonth;
				strYear = Integer.toString(year);
			}
		}

		return strYear + "-" + strMonth;
	}

	/**
	 * 计算当前月份的最后一天
	 * 
	 * @param strDate
	 *            String
	 * @return 返回yyyy-MM-dd格式的当月最后一天
	 */
	public static Date getEndDay(String strDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int month = Integer.parseInt(strDate.substring(5, 7));
		int year = Integer.parseInt(strDate.substring(0, 4));
		int day = 0;
		if (month == 2) {
			if (year % 4 == 0) {
				if (year % 100 == 0) {
					day = 28;
				} else {
					day = 29;
				}
			} else {
				day = 28;
			}
		} else {
			day = days[month - 1];
		}

		String strMonthDate = strDate + "-" + Integer.toString(day);

		try {
			return df.parse(strMonthDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当前时间的第一周的周一？？？
	 * 
	 * @param 无参数
	 * @return 返回yyyy-MM-dd格式 但是具体意思不是很清楚，使用者可以到工具类中具体查看T_T
	 */
	@SuppressWarnings("static-access")
	public static Date getFirstWeekDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dayofweek = cal.get(cal.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(cal.DATE, 1 - dayofweek);
		Date firstDate = cal.getTime();

		String strFirstDate = df.format(firstDate);

		try {
			return df.parse(strFirstDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当前时间的第一周的周末？？？
	 * 
	 * @param 无参数
	 * @return 返回yyyy-MM-dd格式 但是具体意思不是很清楚，使用者可以到工具类中具体查看T_T
	 */
	@SuppressWarnings("static-access")
	public static Date getEndWeekDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dayofweek = cal.get(cal.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
		cal.add(cal.DATE, 7 - dayofweek);
		Date firstDate = cal.getTime();

		String strFirstDate = df.format(firstDate);

		try {
			return df.parse(strFirstDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @TODO <b>日期转化字符串</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @param dt
	 *            Object
	 * @param fmt
	 *            String
	 * @return
	 */
	public static String formatDate(Object dt, String fmt) {
		if (dt == null)
			return null;
		DateFormat df = null;
		if (fmt.equalsIgnoreCase("yyyy"))
			return Integer.toString(getYear(dt));
		else if (fmt.equalsIgnoreCase("MM"))
			return Integer.toString(getMonth(dt));
		else if (fmt.equalsIgnoreCase("mm"))
			return Integer.toString(getMonth(dt));
		else if (fmt.equalsIgnoreCase("dd"))
			return Integer.toString(getDay(dt));
		df = new SimpleDateFormat(fmt);
		Date tmp = convertDateObject(dt);
		if (tmp == null)
			return null;
		return df.format(tmp);
	}

	/**
	 * 
	 * @TODO <b>获得年</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @return
	 */
	public static int getYear() {
		return getYear(null);
	}

	/**
	 * 
	 * @TODO <b>获得年</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @return
	 */
	public static int getYear(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @TODO <b>获得月</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @return
	 */
	public static int getMonth() {
		return getMonth(null);
	}

	public static int getMonth(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @TODO <b>获得日</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @return
	 */
	public static int getDay() {
		return getDay(null);
	}

	public static int getDay(Object dateValue) {
		GregorianCalendar currentDate = new GregorianCalendar();
		if (dateValue != null)
			currentDate.setTime(convertDateObject(dateValue));
		return currentDate.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @TODO <b>日期对象类型转换日期</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @param dt
	 *            Object
	 * @return
	 */
	public static java.util.Date convertDateObject(Object dt) {
		if (dt == null) {
			return null;
		}
		if (!(dt instanceof String) && !(dt instanceof java.sql.Date) && !(dt instanceof java.util.Date) && !(dt instanceof java.lang.Integer) && !(dt instanceof java.lang.Long))
			throw new IllegalArgumentException("日期格式必须是String、Date、Long、Integer类型,请正确输入!");
		Date result = null;

		if (dt instanceof String)
			result = parseString((String) dt);
		else if (dt instanceof java.util.Date)
			result = new java.util.Date(((java.util.Date) dt).getTime());
		else if (dt instanceof java.sql.Date)
			result = new java.util.Date(((java.sql.Date) dt).getTime());
		else
			result = parseString(dt.toString());
		return result;
	}

	/**
	 * 
	 * @TODO <b>将日期字符串或时间转换成时间类型 日期字符串中的日期分隔符可是:"/",".","-"， 返回时间具体到秒
	 *       只提供常用的日期格式处理</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @param dateStr
	 * @return
	 */
	public static Date parseString(String dateStr) {
		if (dateStr == null)
			return null;
		dateStr = dateStr.trim();
		if (dateStr.equals(""))
			return null;
		DateFormat df;
		try {
			if (matchs(dateStr, "^\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}$"))
				df = new SimpleDateFormat("HH:mm:ss");
			else if (matchs(dateStr, "^\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}\\.\\d{1,3}$"))
				df = new SimpleDateFormat("HH:mm:ss.SSS");
			else if (matchs(dateStr, "^\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$"))
				df = new SimpleDateFormat("HH.mm.ss");
			else if (matchs(dateStr, "^\\d{1,2}\\.\\d{1,2}$"))
				df = new SimpleDateFormat("HH.mm");
			else if (matchs(dateStr, "^\\d{1,2}\\:\\d{1,2}$"))
				df = new SimpleDateFormat("HH:mm");
			else {
				dateStr = dateStr.replace("-", "/").replace(".", "/").replace(":", "/").replace("\\", "/");
				// 按概率高低排序
				if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM/dd");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
				else if (matchs(dateStr, "^\\d{8}$"))
					df = new SimpleDateFormat("yyyyMMdd");
				else if (matchs(dateStr, "^\\d{8}\\s+\\d{6}$"))
					df = new SimpleDateFormat("yyyyMMdd HHmmss");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM");
				else if (matchs(dateStr, "^\\d{6}$"))
					df = new SimpleDateFormat("yyyyMM");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,3}$"))
					df = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss/SSS");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{2}$"))
					df = new SimpleDateFormat("yy/MM");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd HH/mm/ss");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd HH/mm");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,3}$"))
					df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSS");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd/HH/mm/ss");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd/HH/mm");
				else if (matchs(dateStr, "^\\d{6}\\s+\\d{6}$"))
					df = new SimpleDateFormat("yyMMdd HHmmss");
				else if (matchs(dateStr, "^\\d{7,9}$"))
					df = new SimpleDateFormat("HHmmssSSS");
				else if (matchs(dateStr, "^\\d{14}$"))
					df = new SimpleDateFormat("yyyyMMddHHmmss");
				else if (matchs(dateStr, "^\\d{15,17}$"))
					df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				else if (matchs(dateStr, "^\\d{12}$"))
					df = new SimpleDateFormat("yyMMddHHmmss");
				else if (matchs(dateStr, "^\\d{4}$"))
					df = new SimpleDateFormat("yyyy");
				else
					df = new SimpleDateFormat("yyyy/MM/dd");
			}
			return df.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @TODO <b>通过正则表达式判断是否匹配</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @param source
	 *            String 代表时间字符串
	 * @param regex
	 *            String 正则表达式
	 * @return
	 */
	public static boolean matchs(String source, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		return m.find();
	}

	/**
	 * 
	 * @TODO <b>返回两个年月数相差月份数</b>
	 * @author 李玮勤
	 * @date 2017-4-12 下午20:59:02
	 * @param source
	 * @param regex
	 * @return 返回相差月份数
	 */
	@SuppressWarnings("deprecation")
	public static int DateResult(String beginDateString, Date endDate, String formatString) {
		int tmpMonth = 0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
		try {
			Date tmpDate = simpleDateFormat.parse(beginDateString);
			// tmpMonth = ((endDate.getYear() - tmpDate.getYear()) * 12) +
			// (endDate.getMonth() - tmpDate.getMonth());
			tmpMonth = ((endDate.getYear() - tmpDate.getYear()) * 12) + (endDate.getMonth() - tmpDate.getMonth());
		} catch (Exception e) {

		}
		return tmpMonth;
	}

	@SuppressWarnings("deprecation")
	public static int MonthResult(Date beginDateString, Date endDate, String formatString) {
		int tmpMonth = 0;
		try {
			Date tmpDate = beginDateString;
			// tmpMonth = ((endDate.getYear() - tmpDate.getYear()) * 12) +
			// (endDate.getMonth() - tmpDate.getMonth());
			tmpMonth = ((endDate.getYear() - tmpDate.getYear()) * 12) + (endDate.getMonth() - tmpDate.getMonth());
		} catch (Exception e) {

		}
		return tmpMonth;
	}

	/**
	 * 两日期相差天数
	 * 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;

	}
}
