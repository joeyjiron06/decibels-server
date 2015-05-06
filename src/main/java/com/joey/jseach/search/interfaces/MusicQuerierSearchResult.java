package com.joey.jseach.search.interfaces;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.AvailabilityWithData;

import java.util.List;

public class MusicQuerierSearchResult {
	private final List<AvailabilityWithData<Artist>> artists;
	private final List<AvailabilityWithData<Album>> albums;
	private final List<AvailabilityWithData<Song>> songs;


	public MusicQuerierSearchResult(List<AvailabilityWithData<Artist>> artists, List<AvailabilityWithData<Album>> albums, List<AvailabilityWithData<Song>> songs) {
		this.artists = artists;
		this.albums = albums;
		this.songs = songs;
	}

	public List<AvailabilityWithData<Artist>> getArtists() {
		return artists;
	}

	public List<AvailabilityWithData<Album>> getAlbums() {
		return albums;
	}

	public List<AvailabilityWithData<Song>> getSongs() {
		return songs;
	}
}
