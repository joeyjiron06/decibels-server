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
import org.eclipse.jetty.server.handler.ContextHandler;

import java.util.*;
import java.util.concurrent.CountDownLatch;


public class JMusicSearchEngine implements MusicSearchEngine {

	private final List<MusicQuerier> musicQueriers;

	public JMusicSearchEngine() {
		musicQueriers = new ArrayList<>();
		musicQueriers.add(new Spotify());
	}


	@Override
	public List<AvailabilitiesList<Artist>> searchArtist(String artist) throws JSearchException {
		return search(artist, SearchType.Artist);
	}

	@Override
	public List<AvailabilitiesList<Album>> searchAlbum(String album) throws JSearchException {
		return search(album, SearchType.Album);
	}

	@Override
	public List<AvailabilitiesList<Song>> searchSong(String song) throws JSearchException {
		return search(song, SearchType.Song);
	}

	@Override
	public MusicSearchEngineResult search(String query, Set<SearchType> searchTypes) throws JSearchException {
		Map<Artist, List<Availibility>> artistsMap = new HashMap<>();


		//query using all music queries and add them to a master list
		for (MusicQuerier musicQuerier : musicQueriers) {
			MusicQuerierSearchResult searchResult = musicQuerier.search(query, searchTypes);

			//look for artist in map
			//if found then update the artist
			//else add it to the map and list
			List<AvailabilityWithData<Artist>> artists = searchResult.getArtists();





		}



		List<AvailabilitiesList<Artist>> artistsList = new ArrayList<>();
		List<AvailabilitiesList<Album>> albumsList = new ArrayList<>();
		List<AvailabilitiesList<Song>> songsList = new ArrayList<>();

		return new MusicSearchEngineResult(artistsList, albumsList, songsList);
	}

	private <T> List<AvailabilitiesList<T>> search(String query, SearchType searchType) throws JSearchException {


		//Ask all queriers to query for the input

		CountDownLatch countDownLatch = new CountDownLatch(musicQueriers.size());
		Map<T, List<Availibility>> map = new HashMap<>();

		for (MusicQuerier musicQuerier : musicQueriers) {
			new SearchThread<T>(searchType, countDownLatch, query, musicQuerier, map).start();
		}

		try {
			//wait for all queries to finish
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new JSearchException(JSearchException.Reason.Unexpected, "no successful search results");
		}


		//convert map to returnable list of data

		List<AvailabilitiesList<T>> results = new ArrayList<>();

		for (T data : map.keySet()) {
			List<Availibility> availibilities = map.get(data);
			results.add(new AvailabilitiesList<>(data, availibilities));
		}

		return results;
	}

	/**
	 * Asks the music querier for the appropriate data given the search type and other parameters.
	 *
	 * */
	private static class SearchThread<T> extends Thread {

		private final CountDownLatch latch;
		private final String query;
		private final SearchType type;
		private final MusicQuerier querier;
		private final Map<T, List<Availibility>> map;

		private SearchThread(SearchType searchType, CountDownLatch latch, String query, MusicQuerier querier, Map<T, List<Availibility>> map) {
			this.type = searchType;
			this.latch = latch;
			this.query = query;
			this.querier = querier;
			this.map = map;
		}

		@Override
		public void run() {
			try {
				List<AvailabilityWithData<T>> results = type.apply(query, querier);


				//add results to the map
				synchronized (map) {
					for (AvailabilityWithData<T> result : results) {
						Availibility availibility = result.getAvailibility();
						T data = result.getData();

						List<Availibility> availibilityList = map.get(data);

						if (availibilityList == null) {
							availibilityList = new ArrayList<>();
							map.put(data, availibilityList);
						}

						boolean containsAvailability = false;

						for (Availibility a : availibilityList) {
							if (availibility.getName().equals(a.getName())) {
								containsAvailability = true;
								break;
							}
						}

						if (!containsAvailability) {
							availibilityList.add(availibility);
						}
					}
				}

			} catch (JSearchException exception) {
				//nothing
			}

			synchronized (latch) {
				latch.countDown();
			}
		}
	}
}
