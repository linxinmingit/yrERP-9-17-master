package com.yr.util;

public class StringUtils {
	public static boolean isNull(String str) {
		if (str==null||str.equals("") ) {
			return true;
		}
		return false;
	}

	public static boolean isNull(Integer str) {
		if (str==null||str.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNull(Double str) {
		if (str==null||str.equals("")) {
			return true;
		}
		return false;
	}
}
