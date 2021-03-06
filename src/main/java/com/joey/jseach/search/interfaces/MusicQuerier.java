package com.joey.jseach.search.interfaces;

import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.SearchType;

import java.util.List;

public interface MusicQuerier {

	/**
	 * @param artist the artist to search for
	 * @throws com.joey.jseach.search.JSearchException if something went wrong with the search
	 *
	 * */
	List<Artist> searchArtist(String artist) throws JSearchException;


	/**
	 * @param album the album to search for
	 * @throws com.joey.jseach.search.JSearchException if something went wrong with the search
	 *
	 * */
	List<Album> searchAlbum(String album) throws JSearchException;


	/**
	 * @param song the song to search for
	 * @throws com.joey.jseach.search.JSearchException if something went wrong with the search
	 *
	 * */
	List<Song> searchSong(String song) throws JSearchException;


	MusicQuerierSearchResult search(String query, List<SearchType> searchTypes) throws JSearchException;
}
