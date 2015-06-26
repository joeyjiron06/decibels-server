package com.joey.jseach.search.interfaces;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;

import java.util.List;

public class MusicQuerierSearchResult {
	private final List<Artist> artists;
	private final List<Album> albums;
	private final List<Song> songs;


	public MusicQuerierSearchResult(List<Artist> artists, List<Album> albums, List<Song> songs) {
		this.artists = artists;
		this.albums = albums;
		this.songs = songs;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public List<Song> getSongs() {
		return songs;
	}
}
