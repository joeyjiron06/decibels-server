package com.joey.jseach.api.spotify;

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
		public ArrayList<SpotifyTrack> items;
	}

}

