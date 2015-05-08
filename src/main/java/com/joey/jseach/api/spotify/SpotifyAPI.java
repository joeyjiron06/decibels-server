package com.joey.jseach.api.spotify;

import retrofit.http.GET;
import retrofit.http.Query;

public interface SpotifyAPI {
	String TYPE_ARTIST = "artist";
	String TYPE_ALBUM = "album";
	String TYPE_TRACK = "track";


	/**
	 * @param query the text to query for.
	 * @param type a comma separated list of types. see types above
	 * */
	@GET("/v1/search")
	SpotifyResult search(@Query(value = "q", encodeName = true) String query, @Query(value = "type") String type);
}
