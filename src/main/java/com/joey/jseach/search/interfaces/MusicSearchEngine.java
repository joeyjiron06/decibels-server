package com.joey.jseach.search.interfaces;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.SearchType;

import java.util.List;
import java.util.Set;


public interface MusicSearchEngine {

	/**
	 * @param artist the artist to search for
	 * @throws com.joey.jseach.search.JSearchException when something went wrong
	 * */
	List<AvailabilitiesList<Artist>> searchArtist(String artist) throws JSearchException;


	/**
	 * @param album the album to search for
	 * */
	List<AvailabilitiesList<Album>> searchAlbum(String album) throws JSearchException;


	/**
	 * @param song the song to search for
	 * */
	List<AvailabilitiesList<Song>> searchSong(String song) throws JSearchException;

	/**
	 * @param query the query for artists, albums, songs
	 * @param searchTypes a set of types to search for
	 * */
	MusicSearchEngineResult search(String query, Set<SearchType> searchTypes) throws JSearchException;
}

