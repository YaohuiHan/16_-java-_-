package system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
class TimeCompare {
	public static long twoStringTime(String beginTime, String endTime) // ʱ��Ƚ�
	{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = null;
		Date end = null;
		try {
			begin = dfs.parse(beginTime);
			end = dfs.parse(endTime);
		} catch (ParseException e) {
		}
		long secondDifference = (end.getTime() - begin.getTime()) / 1000; // ����1000��Ϊ��ת������
		return secondDifference;
	}
}

class TimeAdd {
	public static Date dateTime(Date dateTimeAdd, int add) // ��Date����ʱ���add��
	{
		Date afterDate = new Date(dateTimeAdd.getTime() + (add * 1000));// 1��=1000����
		return afterDate;
	}

	public static String stringTime(String stringTimeAdd, int add) // ��String����ʱ���add��
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTimeTemp = null;

		try {
			dateTimeTemp = sdf.parse(stringTimeAdd);
		} catch (ParseException e) {
		}

		Date afterDate = new Date(dateTimeTemp.getTime() + (add * 1000));// 1��=1000����
		String time = sdf.format(afterDate);
		return time;
	}
 
}

class TimeFormatConversion {
	public static String dateToString(Date date) { // ����ת�ַ������෽��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStringParse = sdf.format(date);
		return dateStringParse;
	}

	public static Date stringToDate(String dateString) { // �ַ���ת����,�෽��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateParse = null;
		try {
			dateParse = sdf.parse(dateString);
		} catch (ParseException e) {
		}
		return dateParse;
	}
}
