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
		Map<String, Artist> artistsMap	= new HashMap<>();
		Map<String, Album> 	albumsMap	= new HashMap<>();
		Map<String, Song> 	songsMap	= new HashMap<>();

		CountDownLatch countDownLatch = new CountDownLatch(musicQueriers.size());

		//query using all music queries and add them to a master list
		for (MusicQuerier musicQuerier : musicQueriers) {
			new Thread() {
				@Override
				public void run() {
					MusicQuerierSearchResult searchResult = musicQuerier.search(query, searchTypes);

					if (searchResult != null) {
						List<Artist> artists = searchResult.getArtists();
						List<Album> albums = searchResult.getAlbums();
						List<Song> songs = searchResult.getSongs();

						synchronized (artistsMap) {
							updateMapWithArtists(artistsMap, artists);
						}

						synchronized (albumsMap) {
							updateMapWithAlbums(albumsMap, albums);
						}

						synchronized (songsMap) {
							updateMapWithSongs(songsMap, songs);
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

		List<Artist> artistsList	= JSU.mapAsList(artistsMap);
		List<Album> albumsList		= JSU.mapAsList(albumsMap);
		List<Song> songsList		= JSU.mapAsList(songsMap);

		return new MusicSearchEngineResult(artistsList, albumsList, songsList);
	}

/* - UTILITY FUNCTIONS */

	private static void updateMapWithArtists(Map<String, Artist> artistsMap, List<Artist> artists) {
		if (artists != null) {
			for (Artist artist : artists) {
				String artistId			= artist.getId();
				Artist artistInMap		= artistsMap.get(artist.getId());

				if ( artistInMap != null ) {
					//add the availabilities from the artist to the artist in the map
					artistInMap.updateWith(artist);
				} else {
					//artist isn't in the map so add it
					artistsMap.put(artistId, artist);
				}
			}
		}
	}

	private static void updateMapWithAlbums(Map<String, Album> albumsMap, List<Album> albums) {
		if (albums != null) {
			for (Album album : albums) {
				String albumId			= album.getId();
				Album albumInMap		= albumsMap.get(albumId);

				if ( albumInMap != null ) {
					albumInMap.updateWith(album);
				} else {
					albumsMap.put(albumId, album);
				}
			}
		}
	}

	private static void updateMapWithSongs(Map<String, Song> songsMap, List<Song> songs) {
		if (songs != null) {
			for (Song song : songs) {
				String songId		= song.getId();
				Song songInMap		= songsMap.get(songId);

				if ( songInMap != null ) {
					songInMap.updateWith(song);
				} else {
					songsMap.put(songId, song);
				}
			}
		}
	}
}
