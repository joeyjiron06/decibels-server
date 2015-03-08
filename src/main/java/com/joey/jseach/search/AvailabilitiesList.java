package com.joey.jseach.search;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.search.interfaces.Availibility;
import com.joey.jseach.utils.JsonUtils;

import java.util.List;

public class AvailabilitiesList<T> implements JsonSerializable {

	private final T data;
	private final List<Availibility> availibilityList;

	public AvailabilitiesList(T data, List<Availibility> availibilityList) {
		this.data = data;
		this.availibilityList = availibilityList;
	}

	public T getData() {
		return data;
	}

	public List<Availibility> getAvailibilityList() {
		return availibilityList;
	}

	private String listToString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Availibility availibility : availibilityList) {
			stringBuilder.append(availibility.toPrettyString());
		}
		return stringBuilder.toString();
	}

	@Override
	public String toString() {
		return data + "\t" + listToString();
	}

	@Override
	public JsonObject toJson() {
		if (data instanceof JsonSerializable) {
			JsonSerializable jsonSerializable = (JsonSerializable) data;
			JsonObject json = jsonSerializable.toJson();
			json.add("availabilites", JsonUtils.toJson(availibilityList));
			return json;
		}
		return null;
	}
}
