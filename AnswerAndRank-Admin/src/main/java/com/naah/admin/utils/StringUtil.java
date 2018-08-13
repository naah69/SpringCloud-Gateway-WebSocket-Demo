package com.naah.admin.utils;

/**
 * 字符工具类
 * @author dazhi
 */
public class StringUtil {

	public static String initialsUpper(String str){
		if(Character.isLowerCase(str.charAt(0))){
			str = Character.toString(Character.toUpperCase(str.charAt(0)))
					+str.substring(1, str.length());
		}
		return str;
	}
}
