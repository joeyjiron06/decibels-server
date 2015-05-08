package com.joey.jseach.search.interfaces;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.utils.JSU;
import spark.utils.CollectionUtils;

import java.util.*;


public interface MusicSearchEngine {

	List<SearchType> SEARCH_TYPES_ALL = Arrays.asList(SearchType.values());

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
	MusicSearchEngineResult search(String query, List<SearchType> searchTypes) throws JSearchException;
}

