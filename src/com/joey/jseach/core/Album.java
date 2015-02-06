package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;

public class Album implements JsonSerializable {
	private final String name;
	private final String image;

	public Album(String name, String image) {
		this.name = name;
		this.image = image;
	}

    public String getName() {
        return name;
    }

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "name " + name +
				" img " + image;
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
		json.addProperty("name", name);
		json.addProperty("image", image);
		return json;
	}
}
