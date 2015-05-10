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
		public List<Artist> items;
	}

	public static class Artist {
		public String id;//spotify id
		public String name;
		public String uri;//spotify uri for artist
		public String type;//should always be artist
		public int popularity;//0-100
		public String href;//A link to the Web API endpoint providing full details of the artist.
		public List<String> genres;

		@SerializedName("external_urls")
		public ExternalUrls externalUrls;

		public List<Image> images;
	}


/* - ALBUMS */

	public static class Albums {
		public List<Album> items;
	}

	public static class Album {
		public String id;
		public String name;
		public List<Image> images;

		@SerializedName("external_urls")
		public ExternalUrls externalUrls;
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
		public Album album;
		public ArrayList<Artist> artists;

		@SerializedName("duration_ms")
		public int durationMs;
	}

/* - OTHER */

	public static class ExternalUrls {
		public String spotify;
	}

	public static class Image {
		public int height;
		public int width;
		public String url;
	}
}

