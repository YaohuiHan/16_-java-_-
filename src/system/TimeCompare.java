package system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
class TimeCompare {
	public static long twoStringTime(String beginTime, String endTime) // 时间比较
	{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = null;
		Date end = null;
		try {
			begin = dfs.parse(beginTime);
			end = dfs.parse(endTime);
		} catch (ParseException e) {
		}
		long secondDifference = (end.getTime() - begin.getTime()) / 1000; // 除以1000是为了转换成秒
		return secondDifference;
	}
}

class TimeAdd {
	public static Date dateTime(Date dateTimeAdd, int add) // 将Date类型时间加add秒
	{
		Date afterDate = new Date(dateTimeAdd.getTime() + (add * 1000));// 1秒=1000毫秒
		return afterDate;
	}

	public static String stringTime(String stringTimeAdd, int add) // 将String类型时间加add秒
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTimeTemp = null;

		try {
			dateTimeTemp = sdf.parse(stringTimeAdd);
		} catch (ParseException e) {
		}

		Date afterDate = new Date(dateTimeTemp.getTime() + (add * 1000));// 1秒=1000毫秒
		String time = sdf.format(afterDate);
		return time;
	}
 
}

class TimeFormatConversion {
	public static String dateToString(Date date) { // 日期转字符串，类方法
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStringParse = sdf.format(date);
		return dateStringParse;
	}

	public static Date stringToDate(String dateString) { // 字符串转日期,类方法
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateParse = null;
		try {
			dateParse = sdf.parse(dateString);
		} catch (ParseException e) {
		}
		return dateParse;
	}
}
