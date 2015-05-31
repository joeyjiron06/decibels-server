package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.utils.JSU;

import java.util.ArrayList;
import java.util.List;

public class Artist implements JsonSerializable {
	private final String name;
	private final Images images;

    public Artist(String name) {
		this.name = name;
		this.images = new Images();
    }

	public void addImages(List<Image> images) {
		this.images.addImages(images);
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + name.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof Artist) {
			return ((Artist) obj).name.equals(this.name);
		}

		return false;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JSU.safeAdd(json, "name", name);
		JSU.safeAdd(json, "images", images.toJson());
		return json;
	}
}
