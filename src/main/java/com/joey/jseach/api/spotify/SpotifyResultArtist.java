package com.joey.jseach.api.spotify;

import com.joey.jseach.core.Artist;
import com.joey.jseach.search.AvailabilityWithData;
import com.joey.jseach.search.Converter;
import com.joey.jseach.search.interfaces.Availibility;

import java.util.ArrayList;
import java.util.List;

public class SpotifyResultArtist {

	public Artists artists;

	public static class Artists {
		public int total;
		public int limit;
		public ArrayList<SpotifyArtist> items;
	}

	public static class SpotifyArtist {
		public String id;//spotify id
		public String name;
		public String uri;//spotify uri for artist
		public String type;//should always be artist
		public int popularity;//0-100
		public String href;//A link to the Web API endpoint providing full details of the artist.
		public List<String> genres;
	}

}
