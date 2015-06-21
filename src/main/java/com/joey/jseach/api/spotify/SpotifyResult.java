package com.joey.jseach.api.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpotifyResult {

	public Artists artists;
	public Albums albums;
	public Tracks tracks;

/* - ARTISTS */

	public static class Artists {
		public int total;
		public int limit;
		public List<SpotifyArtist> items;
	}


/* - ALBUMS */

	public static class Albums {
		public List<SpotifyAlbum> items;
	}

	/* - SONGS */

	public static class Tracks {
		public ArrayList<Track> items;
	}

	public static class Track {
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
}

