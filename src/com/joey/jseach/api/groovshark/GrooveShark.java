package com.joey.jseach.api.groovshark;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import com.joey.jseach.search.JSearchCallback;
import com.joey.jseach.search.SearchItem;
import com.joey.jseach.search.inter.MusicQuerier;

import java.util.List;

public class GrooveShark implements MusicQuerier {
	private static final String API_KEY = "e905985a56db17009410ed055952269e";

	private final TinySongAPI tinySongAPI;

	public GrooveShark() {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://tinysong.com")
				.setRequestInterceptor(new RequestInterceptor() {
					@Override
					public void intercept(RequestFacade requestFacade) {
						requestFacade.addPathParam("key", API_KEY);
					}
				})
				.build();

		tinySongAPI = restAdapter.create(TinySongAPI.class);
	}

	@Override
	public void searchArtist(String artist, JSearchCallback<List<SearchItem<Artist>>> cb) {
//		tinySongAPI.search(artist, );
	}

	@Override
	public void searchAlbum(String album, JSearchCallback<List<SearchItem<Album>>> cb) {

	}

	@Override
	public void searchSong(String song, JSearchCallback<List<SearchItem<Song>>> cb) {

	}
}
