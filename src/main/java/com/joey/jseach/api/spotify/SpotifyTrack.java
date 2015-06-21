package com.joey.jseach.api.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpotifyTrack {
	//album
	//artist
	public int discNumber;
	public boolean explicit;
	public String href;//deep link url
	public String id;
	public String name;
	@SerializedName("external_urls")
	public ExternalUrls externalUrls;
	public boolean isPlayable;
	public int trackNumber;
	public SpotifyAlbum album;
	public ArrayList<SpotifyArtist> artists;

	@SerializedName("duration_ms")
	public int durationMs;
}
