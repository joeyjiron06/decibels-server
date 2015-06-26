package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.utils.JSU;
import com.joey.jseach.search.interfaces.Availability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Artist implements JsonSerializable {
	private final String id;
	private final String name;
	private final Images images;
	private final List<Availability> availabilities;

	public Artist(String name) {
		this.name 			= name;
		this.id				= name;
		this.images 		= new Images();
		this.availabilities = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void addImages(List<Image> images) {
		this.images.addImages(images);
	}

	public void addAvailability(Availability availability) {
		availabilities.add(availability);
	}

	public void updateWith(Artist other) {
		if ( other != null ) {
			this.images.addImages(other.images);
			this.availabilities.addAll( other.availabilities );
		}
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + id.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (! (obj instanceof Artist)) {
			return false;
		}

		Artist other = (Artist) obj;

		return this.id.equals(other.id);
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JSU.safeAdd(json, "name", name);
		JSU.safeAdd(json, "images", images.toJson());
		JSU.safeAdd(json, "availabilities", JSU.toJson(availabilities));
		return json;
	}
}
