package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.utils.JSU;

import java.util.ArrayList;
import java.util.List;

public class Images implements JsonSerializable {
	private final List<Image> small;
	private final List<Image> medium;
	private final List<Image> large;

	public Images() {
		small = new ArrayList<>();
		medium = new ArrayList<>();
		large =  new ArrayList<>();
	}

	public void addImages(List<Image> images) {
		for (Image image : images) {
			if (image.getArea() < 10000) { // 100 * 100
				small.add(image);
			} else if (image.getArea() < 90000) { // 300 * 300
				medium.add(image);
			} else { // large
				large.add(image);
			}
		}
	}

	public void addImages(Images other) {
		if ( other != null ) {
			this.addImages( other.small );
			this.addImages( other.medium );
			this.addImages( other.large );
		}
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.add("small", JSU.toJson(small));
		json.add("medium", JSU.toJson(medium));
		json.add("large", JSU.toJson(large));
		return json;
	}
}
