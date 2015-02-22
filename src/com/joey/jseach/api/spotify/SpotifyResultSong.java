package com.joey.jseach.api.spotify;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpotifyResultSong {
	public Tracks tracks;

	public static class Tracks {

		public ArrayList<SpotifySong> items;
	}

	public static class Album {
		public String name;
	}

	public static class Artist {
		public String name;
	}

	public static class SpotifySong {
		//album
		//artist
		public int discNumber;
		public int durationMs;
		public boolean explicit;
		public String href;//deep link url
		public String id;
		public String name;
		@SerializedName("external_urls")
		public SpotifyExternalUrls externalUrls;
		public boolean isPlayable;
		public int trackNumber;
		public Album album;
		public ArrayList<Artist> artists;
	}


}
