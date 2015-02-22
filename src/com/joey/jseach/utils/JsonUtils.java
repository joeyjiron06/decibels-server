package com.joey.jseach.utils;

import com.google.gson.JsonArray;
import com.joey.jseach.network.JsonSerializable;

import java.util.List;

public class JsonUtils {
	private JsonUtils() {
		//do nothing
	}

	public static JsonArray toJson(List<? extends JsonSerializable> list) {
		if (list != null) {
			JsonArray jsonArray = new JsonArray();
			for (JsonSerializable jsonSerializable : list) {
				jsonArray.add(jsonSerializable.toJson());
			}
			return jsonArray;
		}
		return null;
	}
}
