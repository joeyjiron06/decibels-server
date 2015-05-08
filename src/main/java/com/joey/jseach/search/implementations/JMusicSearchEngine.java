package com.joey.jseach.search.implementations;

import com.joey.jseach.api.spotify.Spotify;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.AvailabilityWithData;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.search.interfaces.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;


public class JMusicSearchEngine implements MusicSearchEngine {

	private final List<MusicQuerier> musicQueriers;

	private static final List<SearchType> SEARCH_TYPE_ARTIST = Collections.singletonList(SearchType.Artist);
	private static final List<SearchType> SEARCH_TYPE_ALBUM = Collections.singletonList(SearchType.Album);
	private static final List<SearchType> SEARCH_TYPE_SONG = Collections.singletonList(SearchType.Song);

	public JMusicSearchEngine() {
		musicQueriers = new ArrayList<>();
		musicQueriers.add(new Spotify());
	}

/* - MusicSearchEngine INTERFACE */

	@Override
	public List<AvailabilitiesList<Artist>> searchArtist(String artist) throws JSearchException {
		MusicSearchEngineResult result =  search(artist, SEARCH_TYPE_ARTIST);

		if (result != null) {
			return result.getArtists();
		}

		return null;
	}

	@Override
	public List<AvailabilitiesList<Album>> searchAlbum(String album) throws JSearchException {
		MusicSearchEngineResult result =  search(album, SEARCH_TYPE_ALBUM);

		if (result != null) {
			return result.getAlbums();
		}

		return null;
	}

	@Override
	public List<AvailabilitiesList<Song>> searchSong(String song) throws JSearchException {
		MusicSearchEngineResult result =  search(song, SEARCH_TYPE_SONG);

		if (result != null) {
			return result.getSongs();
		}

		return null;
	}

	@Override
	public MusicSearchEngineResult search(String query, List<SearchType> searchTypes) throws JSearchException {
		Map<Artist, List<Availibility>> artistsMap = new HashMap<>();
		Map<Album, List<Availibility>> albumsMap = new HashMap<>();
		Map<Song, List<Availibility>> songsMap = new HashMap<>();

		CountDownLatch countDownLatch = new CountDownLatch(musicQueriers.size());

		//query using all music queries and add them to a master list
		for (MusicQuerier musicQuerier : musicQueriers) {
			new Thread() {
				@Override
				public void run() {
					MusicQuerierSearchResult searchResult = musicQuerier.search(query, searchTypes);

					if (searchResult != null) {
						List<AvailabilityWithData<Artist>> artists = searchResult.getArtists();
						List<AvailabilityWithData<Album>> albums = searchResult.getAlbums();
						List<AvailabilityWithData<Song>> songs = searchResult.getSongs();

						synchronized (artistsMap) {
							populateMap(artistsMap, artists);
						}

						synchronized (albumsMap) {
							populateMap(albumsMap, albums);
						}

						synchronized (songsMap) {
							populateMap(songsMap, songs);
						}
					}

					synchronized (countDownLatch) {
						countDownLatch.countDown();
					}
				}
			}.start();
		}

		//wait for all threads to finish
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new JSearchException(JSearchException.Reason.Unexpected, "no successful search results");
		}

		List<AvailabilitiesList<Artist>> artistsList = convertMap(artistsMap);
		List<AvailabilitiesList<Album>> albumsList = convertMap(albumsMap);
		List<AvailabilitiesList<Song>> songsList = convertMap(songsMap);

		return new MusicSearchEngineResult(artistsList, albumsList, songsList);
	}

/* - UTILITY FUNCTIONS */

	private static <T> void populateMap(Map<T, List<Availibility>> dataMap, List<AvailabilityWithData<T>> dataList) {
		if (dataList != null) {
			for (AvailabilityWithData<T> availabilityWithData : dataList) {
				T data = availabilityWithData.getData();
				Availibility availibility = availabilityWithData.getAvailibility();
				//look for artist in map
				List<Availibility> availibilities = dataMap.get(data);
				if (availibilities == null) {
					availibilities = new ArrayList<>();
					dataMap.put(data, availibilities);
				}

				//if availibility isnt in the list then add it
				if (!contains(availibilities, availibility)) {
					availibilities.add(availibility);
				}

			}
		}
	}

	private static <T> List<AvailabilitiesList<T>> convertMap(Map<T, List<Availibility>> dataMap) {
		List<AvailabilitiesList<T>> results = new ArrayList<>();

		for (T data : dataMap.keySet()) {
			List<Availibility> availibilities = dataMap.get(data);
			results.add(new AvailabilitiesList<>(data, availibilities));
		}

		return results;
	}

	private static boolean contains(List<Availibility> availibilities, Availibility availibility) {
		String name = availibility.getName();
		if (name != null) {
			for (Availibility a : availibilities) {
				if (name.equals(a.getName())) {
					return true;
				}
			}
		}

		return false;
	}
}
