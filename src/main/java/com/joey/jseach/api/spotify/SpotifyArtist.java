package com.joey.jseach.api.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyArtist {
	public String id;//spotify id
	public String name;
	public String uri;//spotify uri for artist
	public String type;//should always be artist
	public int popularity;//0-100
	public String href;//A link to the Web API endpoint providing full details of the artist.
	public List<String> genres;

	@SerializedName("external_urls")
	public ExternalUrls externalUrls;

	public List<SpotifyImage> images;
}
