package com.joey.jseach.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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

	public static boolean safeEquals(String str1, String str2) {

		if ( str1 == null && str2 == null) {
			return true;
		}

		if ( str1 == null ) {
			return false;
		}

		if ( str2 == null ) {
			return false;
		}

		return str1.equals( str2 );
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
	public static String combine(Collection<?> collection, String separator) {
		if (collection != null) {

			StringBuilder stringBuilder = new StringBuilder();

			int i = 0;
			int size = collection.size();
			for (Object string : collection) {
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

	public static <K, V> List<V> mapAsList(Map<K, V> map) {
		if ( map != null ) {
			return new ArrayList<>( map.values() );
		}

		return null;
	}

	public static <T> T findInCollection(Collection<T> collection, Predicate<T> predicate) {
		for (T item : collection) {
			if ( predicate.test(item) ) {
				return item;
			}
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

	public static void safeAdd(JsonObject json, String key, String value) {
		if (!JSU.isNullOrEmpty(value)) {
			json.addProperty(key, value);
		}
	}

	public static void safeAdd(JsonObject json, String key, JsonElement value) {
		if (value != null) {
			json.add(key, value);
		}
	}

	public static void safeAddPositive(JsonObject json, String key, int value) {
		if (value > 0) {
			json.addProperty(key, value);
		}
	}

	public static String safeGetString(JsonObject json, String key) {
		if (json != null) {
			try {
				return json.get(key).getAsString();
			} catch (Exception e) {
			}
		}

		return null;
	}
}
