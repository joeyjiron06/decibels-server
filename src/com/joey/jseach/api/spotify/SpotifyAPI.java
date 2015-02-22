package com.joey.jseach.api.spotify;

import retrofit.http.GET;
import retrofit.http.Query;

public interface SpotifyAPI {
	/**
	 * @param artist the query string
	 * @param cb     the callback received when query is finished
	 * */
	@GET("/v1/search?type=artist")
	SpotifyResultArtist searchArtist(@Query(value = "q", encodeName = true) String artist);

	/**
	 * @param album the query string
	 * */
	@GET("/v1/search?type=album")
	SpotifyResultAlbum searchAlbum(@Query(value = "q", encodeName = true) String album);

	/**
	 * @param song the query string
	 * */
	@GET("/v1/search?type=track")
	SpotifyResultSong searchSong(@Query(value = "q", encodeName = true) String song);
}
