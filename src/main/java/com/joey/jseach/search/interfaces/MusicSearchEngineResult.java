package com.joey.jseach.search.interfaces;

import com.google.gson.JsonObject;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.utils.JsonUtils;

import java.util.List;

public class MusicSearchEngineResult implements JsonSerializable {
	private final List<AvailabilitiesList<Artist>> artists;
	private final List<AvailabilitiesList<Album>> albums;
	private final List<AvailabilitiesList<Song>> songs;

	public MusicSearchEngineResult(List<AvailabilitiesList<Artist>> artists, List<AvailabilitiesList<Album>> albums, List<AvailabilitiesList<Song>> songs) {
		this.artists = artists;
		this.albums = albums;
		this.songs = songs;
	}


	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();

		json.add("artists", JsonUtils.toJson(artists));
		json.add("albums", JsonUtils.toJson(albums));
		json.add("songs", JsonUtils.toJson(songs));

		return json;
	}
}
