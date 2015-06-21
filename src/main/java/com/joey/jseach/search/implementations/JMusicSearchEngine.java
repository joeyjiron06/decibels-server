package com.joey.jseach.search.implementations;

import com.joey.jseach.api.spotify.Spotify;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.search.interfaces.*;
import com.joey.jseach.utils.JSU;

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
	public List<Artist> searchArtist(String artist) throws JSearchException {
		MusicSearchEngineResult result =  search(artist, SEARCH_TYPE_ARTIST);

		if (result != null) {
			return result.getArtists();
		}

		return null;
	}

	@Override
	public List<Album> searchAlbum(String album) throws JSearchException {
		MusicSearchEngineResult result =  search(album, SEARCH_TYPE_ALBUM);

		if (result != null) {
			return result.getAlbums();
		}

		return null;
	}

	@Override
	public List<Song> searchSong(String song) throws JSearchException {
		MusicSearchEngineResult result =  search(song, SEARCH_TYPE_SONG);

		if (result != null) {
			return result.getSongs();
		}

		return null;
	}

	@Override
	public MusicSearchEngineResult search(String query, List<SearchType> searchTypes) throws JSearchException {
		List<Artist> artists			= new ArrayList<>();
		List<Album> albums				= new ArrayList<>();
		List<Song> songs				= new ArrayList<>();

		CountDownLatch countDownLatch = new CountDownLatch(musicQueriers.size());

		//query using all music queries and add them to a master list
		for (MusicQuerier musicQuerier : musicQueriers) {
			new Thread() {
				@Override
				public void run() {
					MusicQuerierSearchResult searchResult = musicQuerier.search(query, searchTypes);

					if (searchResult != null) {

						synchronized (artists) {
							addAllArtists(searchResult.getArtists(), artists);
						}

						synchronized (albums) {
							addAllAlbums(searchResult.getAlbums(), albums);
						}

						synchronized (songs) {
							addAllSongs(searchResult.getSongs(), songs);
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

		return new MusicSearchEngineResult(artists, albums, songs);
	}

/* - UTILITY FUNCTIONS */

	private static void addAllArtists(List<Artist> source, List<Artist> destination) {
		if ( source == null || destination == null) {
			return;
		}


		for (Artist currentArtist : source) {
			Artist artistInDestination		= JSU.findInCollection(destination, artist -> artist.getId().equals(currentArtist.getId()));

			if ( artistInDestination != null ) {
				artistInDestination.updateWith(currentArtist);
			} else {
				destination.add(currentArtist );
			}
		}
	}

	private static void addAllAlbums(List<Album> source, List<Album> destination) {
		if ( source == null || destination == null) {
			return;
		}


		for (Album currentAlbum : source) {
			Album albumInDestination		= JSU.findInCollection(destination, album -> album.getId().equals(currentAlbum.getId()));

			if ( albumInDestination != null ) {
				albumInDestination.updateWith( currentAlbum );
			} else {
				destination.add( currentAlbum );
			}
		}
	}

	private static void addAllSongs(List<Song> source, List<Song> destination) {
		if ( source == null || destination == null) {
			return;
		}


		for (Song currentSong : source) {
			Song songInDestination		= JSU.findInCollection(destination, song -> song.getId().equals(currentSong.getId()));

			if ( songInDestination != null ) {
				songInDestination.updateWith(currentSong);
			} else {
				destination.add( currentSong );
			}
		}
	}
}
