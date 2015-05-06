package com.joey.jseach.utils;


/*-JSearch Utilites
 */
public class Strings {


	public static boolean isNullOrEmpty(String string) {
		return string == null || string.length() == 0 || string.trim().length() == 0;
	}

	/**
	 * @param input String of format string1, string2,... etc.
	 * @return an array of strings
	 * */
	public static String[] safeSplit(String input) {
		if (!isNullOrEmpty(input)) {
			return input.split(",");
		}

		//return empty list
		return new String[]{};
	}
}
