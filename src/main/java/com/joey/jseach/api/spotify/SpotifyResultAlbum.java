package com.joey.jseach.api.spotify;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyResultAlbum {

	public Albums albums;

	public static class Albums {
		public List<SpotifyAlbum> items;
	}

	public static class Image {
		public int height;
		public int width;
		public String url;
	}

	public static class SpotifyAlbum {
		public String id;
		public String name;
		public List<Image> images;

		@SerializedName("external_urls")
		public SpotifyExternalUrls externalUrls;
	}



}
