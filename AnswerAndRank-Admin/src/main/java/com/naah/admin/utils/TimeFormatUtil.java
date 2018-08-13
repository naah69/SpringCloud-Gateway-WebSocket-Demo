package com.naah.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author dazhi
 */
public class TimeFormatUtil {

	private static final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getTimeStr(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIMEFORMAT);
		return dateFormat.format(date);
	}

	public static String getTimeStr(Long timeLong){
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIMEFORMAT);
		return dateFormat.format(timeLong);
	}

	public static Date getDateForStr(String time){
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIMEFORMAT);
		Date parse = null;
		try {
			parse = dateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}
}
