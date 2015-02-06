package com.joey.jseach.search.interfaces;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.search.JSearchException;

import java.util.List;


public interface MusicSearchEngine {

	/**
	 * @param artist the artist to search for
	 * @param cb the callback called when the query starts and finishes
	 * @throws com.joey.jseach.search.JSearchException when something went wrong
	 * */
	List<AvailabilitiesList<Artist>> searchArtist(String artist) throws JSearchException;


	/**
	 * @param album the album to search for
	 * @param cb the callback called when the query starts and finishes
	 *
	 * */
	List<AvailabilitiesList<Album>> searchAlbum(String album) throws JSearchException;


	/**
	 * @param song the song to search for
	 * @param cb the callback called when the query starts and finishes
	 *
	 * */
	List<AvailabilitiesList<Song>> searchSong(String song) throws JSearchException;
}

