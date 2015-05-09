package com.joey.jseach.utils;


import com.google.gson.JsonArray;
import com.joey.jseach.network.JsonSerializable;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

import java.util.Collection;

/**
 * JSearch UTILITIES
 * */
public class JSU {

	private JSU() {
		//dont allow instantiations of this object
	}

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

		return null;
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

	public static JsonArray toJson(Collection<? extends JsonSerializable> list) {
		if (list != null) {
			JsonArray jsonArray = new JsonArray();
			for (JsonSerializable jsonSerializable : list) {
				jsonArray.add(jsonSerializable.toJson());
			}
			return jsonArray;
		}
		return null;
	}

/* - A P P  S P E C I F I C  U T I L S */

	private static String toString(RetrofitError error) {
		StringBuilder builder = new StringBuilder("RetrofitError : ");

		if (error != null) {
			Response response = error.getResponse();
			if (response != null) {
				builder.append(" url(")
						.append(response.getUrl())
						.append(")");

				builder.append(" httpStatus(")
						.append(response.getStatus())
						.append(")");

				builder.append(" reason(")
						.append(response.getReason())
						.append(")");

				TypedInput input = response.getBody();
				if (input != null) {
					builder.append(" body(");
					if (input instanceof TypedByteArray) {
						TypedByteArray typedByteArray = (TypedByteArray) input;
						builder.append(new String(typedByteArray.getBytes()));
					} else {
						builder.append(input.toString());
					}
					builder.append(")");
				}
			}
		}

		return builder.toString();
	}
}
