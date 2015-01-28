package com.joey.jseach.search.impl;

import com.joey.jseach.api.groovshark.GrooveShark;
import com.joey.jseach.api.spotify.Spotify;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.JSearchCallback;
import com.joey.jseach.search.JSearchError;
import com.joey.jseach.search.SearchItem;
import com.joey.jseach.search.inter.MusicQuerier;
import com.joey.jseach.search.inter.MusicSearchEngine;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class JMusicSearchEngine implements MusicSearchEngine {

	private enum SearchType {
		Artist,
		Album,
		Song
	}

	private final List<MusicQuerier> musicQueriers;

	public JMusicSearchEngine() {
		musicQueriers = new ArrayList<>();
		musicQueriers.add(new GrooveShark());
		musicQueriers.add(new Spotify());
	}

	@Override
	public void searchArtist(String artist, JSearchCallback<List<SearchItem<Artist>>> cb) {
		searchByType(SearchType.Artist, artist, cb);
	}

	@Override
	public void searchAlbum(String album, JSearchCallback<List<SearchItem<Album>>> cb) {
		searchByType(SearchType.Album, album, cb);
	}

	@Override
	public void searchSong(String song, JSearchCallback<List<SearchItem<Song>>> cb) {
		searchByType(SearchType.Song, song, cb);
	}

	private void searchByType(SearchType type, String query, JSearchCallback<?> cb) {
		cb.onPreload();

		MultiCallbackListener<List<SearchItem<?>>> multiCallbackListener = new MultiCallbackListener<>((JSearchCallback)cb, musicQueriers.size());

		for (MusicQuerier musicQuerier : musicQueriers) {
			switch (type) {
				case Artist:
					musicQuerier.searchArtist(query, (JSearchCallback) multiCallbackListener);
					break;
				case Album:
					musicQuerier.searchAlbum(query, (JSearchCallback) multiCallbackListener);
					break;
				case Song:
					musicQuerier.searchSong(query, (JSearchCallback) multiCallbackListener);
					break;
				default:
					throw new IllegalStateException();
			}
		}
	}

	/**
	 * Listens for the specified number of callbacks then invokes the passed in callback methods.
	 *
	 * */
	private static class MultiCallbackListener<T> implements JSearchCallback<List<SearchItem<T>>> {

		private final JSearchCallback<List<SearchItem<T>>> callback;
		private final List<SearchItem<T>> results;
		private final List<JSearchError> errors;
		private final int targetCallbackCount;
		private boolean atLeastOneSuccess;
		private int checkCount;

		public MultiCallbackListener(JSearchCallback<List<SearchItem<T>>> callback, int targetCallbackCount) {
			this.callback = callback;
			this.targetCallbackCount = targetCallbackCount;
			this.results = new ArrayList<>();
			this.errors = new ArrayList<>();
		}

		private void check() {
			checkCount++;

			if (checkCount == targetCallbackCount) {
				if (atLeastOneSuccess) {
					callback.onSuccess(Collections.unmodifiableList(results));
				} else {
					JSearchError error = !errors.isEmpty() ? errors.get(0) : new JSearchError(JSearchError.Cause.NETWORK_ERROR, null);
					callback.onFailure(error);
				}
			}
		}

		@Override
		public void onPreload() {
			//do nothing
		}

		@Override
		public void onSuccess(List<SearchItem<T>> data) {
			atLeastOneSuccess = true;
			results.addAll(data);
			check();
		}


		@Override
		public void onFailure(JSearchError error) {
			errors.add(error);
			check();
		}
	}
}
