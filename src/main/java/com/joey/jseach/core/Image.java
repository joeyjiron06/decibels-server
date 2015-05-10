package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;

public class Image implements JsonSerializable {
	private final int width;
	private final int height;
	private final String url;


	public Image(int width, int height, String url) {
		this.width = width;
		this.height = height;
		this.url = url;
	}

	@Override
	public String toString() {
		return String.format("(width:%d, height:%d, url:%s)", width, height, url);
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("width", width);
		json.addProperty("height", height);
		json.addProperty("url", url);
		return json;
	}
}
