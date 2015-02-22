package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;

public class Artist implements JsonSerializable {
    private final String name;
	private final String img;

    public Artist(String name, String img) {
        this.name = name;
	    this.img = img;
    }

    public String getName() {
        return name;
    }

	public String getImg() {
		return img;
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
		json.addProperty("name", name);
		json.addProperty("image", img);
		return json;
	}
}
