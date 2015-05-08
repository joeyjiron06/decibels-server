package com.joey.jseach.utils;


import java.util.Collection;

/**
 * JSearch UTILITIES
 * */
public class JSU {

/* - S T R I N G S */

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

	/**
	 * @param collection the collection to combine
	 * @param separator the separator to use for the combining
	 * */
	public static String combine(Collection<String> collection, String separator) {
		if (collection != null) {

			StringBuilder stringBuilder = new StringBuilder();

			int i = 0;
			int size = collection.size();
			for (String string : collection) {
				stringBuilder.append(string);

				if ((i+1) < size) {
					stringBuilder.append(separator);
				}

				i++;
			}

			return stringBuilder.toString();
		}

		return null;
	}

/* - C O L L E C T I O N S */

	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
}
