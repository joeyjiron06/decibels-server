package com.joey.jseach.search.inter;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.JSearchCallback;
import com.joey.jseach.search.SearchItem;

import java.util.List;

public interface MusicQuerier {

	/**
	 * @param artist the artist to com.joey.jseach.search for
	 * @param cb the callback called when the query starts and finishes
	 *
	 * */
	void searchArtist(String artist, JSearchCallback<List<SearchItem<Artist>>> cb);


	/**
	 * @param album the album to com.joey.jseach.search for
	 * @param cb the callback called when the query starts and finishes
	 *
	 * */
	void searchAlbum(String album, JSearchCallback<List<SearchItem<Album>>> cb);


	/**
	 * @param song the song to com.joey.jseach.search for
	 * @param cb the callback called when the query starts and finishes
	 *
	 * */
	void searchSong(String song, JSearchCallback<List<SearchItem<Song>>> cb);
}
