package com.joey.jseach.search.interfaces;

import com.google.gson.JsonObject;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.utils.JSU;

import java.util.List;

public class MusicSearchEngineResult implements JsonSerializable {
	private final List<Artist> artists;
	private final List<Album> albums;
	private final List<Song> songs;

	public MusicSearchEngineResult(List<Artist> artists, List<Album> albums, List<Song> songs) {
		this.artists = artists;
		this.albums = albums;
		this.songs = songs;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public List<Song> getSongs() {
		return songs;
	}


	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();

		json.add("artists", JSU.toJson(artists));
		json.add("albums", JSU.toJson(albums));
		json.add("songs", JSU.toJson(songs));

		return json;
	}
}
