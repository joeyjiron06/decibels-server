package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.search.interfaces.Availability;
import com.joey.jseach.utils.JSU;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Album implements JsonSerializable {
	private final String id;
	private final String name;
	private final Images images;
	private final List<Availability> availabilities;
	private String artistName;

	public Album(String name) {
		this.name 				= name;
		this.id   				= name;
		this.images 			= new Images();
		this.availabilities = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void updateWith(Album other) {
		if ( other != null ) {
			if ( JSU.isNullOrEmpty(this.artistName) ) {
				this.artistName = other.artistName;
			}

			this.availabilities.addAll( other.availabilities );

			this.images.addImages( other.images );
		}
	}

	public void addImages(List<Image> images) {
		this.images.addImages( images );
	}

	public void addAvailability(Availability availability) {
		availabilities.add( availability );
	}

	@Override
	public String toString() {
		return String.format("(name:%s, imgs:[%s])", name, images.toString());
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

		if (!(obj instanceof Album)) {
			return false;
		}

		Album other = (Album) obj;

		return other.id.equals(this.id);
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JSU.safeAdd(json, "name", name);
		JSU.safeAdd(json, "artist", artistName);
		JSU.safeAdd(json, "images", images.toJson());
		JSU.safeAdd(json, "availabilities", JSU.toJson(availabilities));
		return json;
	}
}
