package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.utils.JSU;

import java.util.ArrayList;
import java.util.List;

public class Song implements JsonSerializable {
	private final String name;
	private final Images images;
	private String album;
	private String artist;
	private int durationMs;

    public Song(String name) {
		this.name = name;
		this.images = new Images();
	}

	public void addImages(List<Image> images) {
		this.images.addImages(images);
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setDurationMs(int durationMs) {
		this.durationMs = durationMs;
	}

	@Override
	public String toString() {
		return String.format("(name:%s, album:%s, artist:%s, durationMs:%d, images:[%s])",
				name,
				album,
				artist,
				durationMs,
				images.toString());
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

		if (obj instanceof Song) {
			Song other = (Song) obj;
			return other.name.equals(name);
		}

		return false;
	}


	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		JSU.safeAdd(json, "name", name);
		JSU.safeAdd(json, "album", album);
		JSU.safeAdd(json, "artist", artist);
		JSU.safeAdd(json, "images", images.toJson());
		JSU.safeAddPositive(json, "durationMs", durationMs);
		return json;
	}
}
