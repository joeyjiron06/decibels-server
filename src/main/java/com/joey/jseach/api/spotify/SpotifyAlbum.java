package com.joey.jseach.api.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jjiron on 6/20/15.
 */
public class SpotifyAlbum {
	public String id;
	public String name;
	public List<SpotifyImage> images;

	@SerializedName("external_urls")
	public ExternalUrls externalUrls;
}
