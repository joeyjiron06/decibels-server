package com.joey.jseach.search.interfaces;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;

public interface Availibility extends JsonSerializable {
	String getName();
	String getDeepLink();
	String getIcon();

	@Override
	default JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("name", getName());
		json.addProperty("deepLink", getDeepLink());
		json.addProperty("image", getIcon());
		return json;
	}

	default String toPrettyString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("name ");
		stringBuilder.append(getName());
		stringBuilder.append(" deepLink ");
		stringBuilder.append(getDeepLink());
		stringBuilder.append(" icon ");
		stringBuilder.append(getIcon());
		return stringBuilder.toString();
	}
}
