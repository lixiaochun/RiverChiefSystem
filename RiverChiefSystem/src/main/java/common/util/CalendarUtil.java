package common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarUtil {

	/**
	 * 获取一个月的所有日期
	 * 
	 * @param month
	 */
	public static List<String> dayReport(Date month) {

		List<String> list = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(month);// month 为指定月份任意日期
		int year = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH);
		int dayNumOfMonth = CalendarUtil.getDaysByYearMonth(year, m);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始

		for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
			Date d = cal.getTime();
			if (checkHoliday(cal)) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				String df = simpleDateFormat.format(d);
				list.add(df);
			}
		}
		return list;
	}

	/**
	 * 获取指定月份的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 
	 * <p>
	 * Title: checkHoliday
	 * </P>
	 * <p>
	 * Description: TODO 验证日期是否是节假日
	 * </P>
	 * 
	 * @param calendar
	 *            传入需要验证的日期
	 * @return return boolean 返回类型 返回true是节假日，返回false不是节假日 throws date 2014-11-24
	 *         上午10:13:07
	 */
	public static boolean checkHoliday(Calendar calendar) {

		// 判断日期是否是周六周日
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return false;
		}

		return true;
	}
}
