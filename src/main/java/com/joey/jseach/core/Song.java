package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.search.interfaces.Availability;
import com.joey.jseach.utils.JSU;

import java.util.ArrayList;
import java.util.List;

public class Song implements JsonSerializable {

	private final String id;
	private final String name;
	private final String albumName;
	private final String artistName;
	private final Images images;
	private final List<Availability> availabilities;
	private int durationMs;

	public Song(String name, String albumName, String artistName) {
		this.name 			= name;
		this.albumName 		= albumName;
		this.artistName		= artistName;
		this.images 		= new Images();
		this.availabilities = new ArrayList<>();
		this.id				= createId(name, albumName, artistName);
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

	public void setDurationMs(int durationMs) {
		this.durationMs = durationMs;
	}

	public void updateWith(Song other) {
		if ( other != null ) {
			this.images.addImages( other.images );

			this.availabilities.addAll( other.availabilities );

			if ( this.durationMs == 0 ) {
				this.setDurationMs( other.durationMs );
			}
		}
	}

	@Override
	public String toString() {
		return String.format("(id:%s, name:%s, album:%s, artist:%s, durationMs:%d, images:[%s])",
				id,
				name,
				albumName,
				artistName,
				durationMs,
				images.toString());
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + id.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if ( ! (obj instanceof Song) ) {
			return false;
		}

		Song other = (Song) obj;

		return other.id.equals(id);
	}


	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JSU.safeAdd(json, "name", name);
		JSU.safeAdd(json, "album", albumName);
		JSU.safeAdd(json, "artist", artistName);
		JSU.safeAdd(json, "images", images.toJson());
		JSU.safeAdd(json, "availabilities", JSU.toJson(availabilities));
		JSU.safeAddPositive(json, "durationMs", durationMs);
		return json;
	}

	private static String createId(String songName, String albumName, String artistName) {
		StringBuilder stringBuilder = new StringBuilder();
		if ( songName != null ) {
			stringBuilder.append( songName );
		}
		if ( albumName != null ) {
			stringBuilder.append( albumName );
		}
		if ( artistName != null ) {
			stringBuilder.append( albumName );
		}
		return stringBuilder.toString();
	}
}
