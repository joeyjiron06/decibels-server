package com.joey.jseach.core;

import com.google.gson.JsonObject;
import com.joey.jseach.network.JsonSerializable;

public class Song implements JsonSerializable {
	private final String name;
	private final String album;
	private final String artist;
	private final String image;
	private final int durationMs;

    public Song(String name, String album, String artist, String image, int durationMs) {
		this.name = name;
		this.album = album;
		this.artist = artist;
		this.image = image;
		this.durationMs = durationMs;
	}

	public String getName() {
        return name;
    }

	public String getImage() {
		return image;
	}

	public String getAlbum() {
		return album;
	}

	public String getArtist() {
		return artist;
	}

	@Override
	public String toString() {
		return "name " + name +
				" album " + album +
				" artist " + artist +
				" image " + image;
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
		json.addProperty("name", name);
		json.addProperty("album", album);
		json.addProperty("artist", artist);
		json.addProperty("image", image);
		json.addProperty("durationMs", durationMs);
		return json;
	}
}
