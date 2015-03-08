package com.joey.jseach.api.groovshark;

public class TinySongItem {
	public String Url;
	public int SongID;
	public String SongName;
	public int ArtistID;
	public String ArtistName;
	public int AlbumId;
	public String AlbumName;

	private static final String element(String name, String value) {
		return name + ": " + value + ",";
	}

	@Override
	public String toString() {
		return element("url", Url) +
				element("songid", SongID + "") +
				element("songName", SongName) +
				element("artistId", ArtistID+"") +
				element("artistName", ArtistName) +
				element("albumId", AlbumId+"") +
				element("albumName", AlbumName);
	}
}
