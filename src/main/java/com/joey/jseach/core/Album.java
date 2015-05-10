package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.utils.JSU;

import java.util.ArrayList;
import java.util.List;

public class Album implements JsonSerializable {
	private final String name;
	private final List<Image> images;
	private String artist;

	public Album(String name) {
		this.name = name;
		this.images = new ArrayList<>();
	}

	public void addImage(Image image) {
		images.add(image);
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return String.format("(name:%s, imgs:[%s])", name, JSU.combine(images, ","));
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

		if (!(obj instanceof Album)) {
			return false;
		}

		Album other = (Album) obj;
		return other.name.equals(this.name);
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JSU.safeAdd(json, "name", name);
		JSU.safeAdd(json, "artist", artist);
		JSU.safeAdd(json, "images", JSU.toJson(this.images));
		return json;
	}
}
