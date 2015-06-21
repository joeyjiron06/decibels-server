package com.joey.jseach.api.spotify;

import com.joey.jseach.core.Image;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.search.interfaces.MusicQuerierSearchResult;
import com.joey.jseach.utils.Logger;
import com.joey.jseach.utils.JSU;
import retrofit.RestAdapter;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.Converter;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.implementations.JSearchErrorHandler;
import com.joey.jseach.search.interfaces.Availability;
import com.joey.jseach.search.interfaces.MusicQuerier;

import java.util.*;


public class Spotify implements MusicQuerier {

	private static final String TAG = "Spotify";

	private static final Converter<SpotifyArtist, Artist> ArtistConverter = new ArtistConverter();
	private static final Converter<SpotifyAlbum, Album> AlbumConverter = new AlbumConverter();
	private static final Converter<SpotifyResult.Track, Song> SongConverter = new SongConverter();

	private static final List<SearchType> ARTIST_SET = Collections.singletonList(SearchType.Artist);
	private static final List<SearchType> ALBUM_SET = Collections.singletonList(SearchType.Album);
	private static final List<SearchType> SONG_SET = Collections.singletonList(SearchType.Song);

	private final SpotifyAPI spotifyAPI;

	public Spotify() {
		spotifyAPI = new RestAdapter.Builder()
				.setEndpoint("https://api.spotify.com")
				.setErrorHandler(new JSearchErrorHandler())
				.build()
				.create(SpotifyAPI.class);
	}

/* - MusicQuerier INTERFACE */

	@Override
	public List<Artist> searchArtist(String artist) throws JSearchException {
		MusicQuerierSearchResult searchResult = search(artist, ARTIST_SET);
		if (searchResult != null) {
			return searchResult.getArtists();
		}
		return null;
	}

	@Override
	public List<Album> searchAlbum(String album) throws JSearchException {
		MusicQuerierSearchResult searchResult = search(album, ALBUM_SET);
		if (searchResult != null) {
			return searchResult.getAlbums();
		}
		return null;
	}

	@Override
	public List<Song> searchSong(String song) throws JSearchException {
		MusicQuerierSearchResult searchResult = search(song, SONG_SET);
		if (searchResult != null) {
			return searchResult.getSongs();
		}
		return null;
	}

	@Override
	public MusicQuerierSearchResult search(String query, List<SearchType> searchTypes) throws JSearchException {
		// CONVERT APP ENUM TYPES TO SPOTIFY API TYPES
		String types = convertToSpotifyType(searchTypes);

		// QUERY SPOTIFY API
		SpotifyResult spotifyResult = spotifyAPI.search(query, types);

		if (spotifyResult != null) {
			List<Artist> artists = null;
			List<Album> albums = null;
			List<Song> songs = null;

			if (spotifyResult.artists != null) {
				artists = convertAll(spotifyResult.artists.items, ArtistConverter);
			}

			if (spotifyResult.albums != null) {
				albums = convertAll(spotifyResult.albums.items, AlbumConverter);
			}

			if (spotifyResult.tracks != null) {
				songs = convertAll(spotifyResult.tracks.items, SongConverter);
			}

			return new MusicQuerierSearchResult(artists, albums, songs);
		}

		return null;
	}

/* - UTILITY FUNCTIONS */

	private static <I, O> List<O> convertAll(List<I> inputList, Converter<I, O> converter) {
		if (inputList != null) {
			List<O> result = new ArrayList<>();
			for (I input : inputList) {
				O output = converter.convert(input);
				result.add(output);
			}
			return result;
		}

		return null;
	}

	private static String convertToSpotifyType(List<SearchType> searchTypes) {
		List<String> result = new ArrayList<>();

		for (SearchType searchType : searchTypes) {
			String spotifyType = findSpotifyType(searchType);
			if (spotifyType != null) {
				result.add(spotifyType);
			} else {
				log("could not find spotify search type mapping for %s", searchType);
			}
		}

		return JSU.combine(result, ",");
	}

	private static String findSpotifyType(SearchType searchType) {
		switch (searchType) {
			case Artist:
				return SpotifyAPI.TYPE_ARTIST;
			case Album:
				return SpotifyAPI.TYPE_ALBUM;
			case Song:
				return SpotifyAPI.TYPE_TRACK;
			default:
				return null;
		}
	}

	private static void log(String message, Object... args) {
		Logger.log(TAG, String.format(message, args));
	}

/* - CONVERTERS */

	private static List<Image>  extractImages(List<SpotifyImage> images) {
		List<Image> result = null;

		if (!JSU.isNullOrEmpty(images)) {
			result = new ArrayList<>();
			for (SpotifyImage image: images) {
				result.add(new Image(image.width, image.height, image.url));
			}
		}

		return result;
	}

	private static class ArtistConverter implements Converter<SpotifyArtist, Artist> {
		@Override
		public Artist convert(SpotifyArtist spotifyArtist) {

			// CREATE ARTIST
			Artist artist = new Artist(spotifyArtist.name);

			// GET AVAILABILITY
			Availability availability = new SpotifyAvailability(spotifyArtist.externalUrls.spotify);
			artist.addAvailability( availability );

			// GET IMAGES
			List<Image> images = extractImages(spotifyArtist.images);

			if ( ! JSU.isNullOrEmpty(images) ) {
				artist.addImages(images);
			}

			return artist;
		}
	};

	private static class AlbumConverter implements Converter<SpotifyAlbum, Album> {

		@Override
		public Album convert(SpotifyAlbum spotifyAlbum) {

			// CREATE ALBUM
			Album album = new Album(spotifyAlbum.name);

			// GET AVAILABILITY
			Availability availability = new SpotifyAvailability(spotifyAlbum.externalUrls.spotify);
			album.addAvailability( availability );

			// GET IMAGES
			List<Image> images = extractImages(spotifyAlbum.images);

			if ( ! JSU.isNullOrEmpty(images) ) {
				album.addImages(images);
			}

			return album;
		}
	};

	private static class SongConverter implements Converter<SpotifyResult.Track, Song> {
		@Override
		public Song convert(SpotifyResult.Track spotifySong) {

			// CREATE SONG
			Song song = new Song(spotifySong.name);

			// GET AVAILABILITY
			Availability availability = new SpotifyAvailability(spotifySong.externalUrls.spotify);
			song.addAvailability( availability );


			// GET ARTIST NAME
			if ( ! JSU.isNullOrEmpty(spotifySong.artists) ) {
				SpotifyArtist spotifyArtist = spotifySong.artists.get(0);
				song.setArtist(spotifyArtist.name);
			}

			// GET ALBUM NAME
			if (spotifySong.album != null) {
				SpotifyAlbum spotifyAlbum = spotifySong.album;
				song.setAlbum(spotifyAlbum.name);

				// GET IMAGES FROM ALBUM
				List<Image> images = extractImages(spotifyAlbum.images);
				if (!JSU.isNullOrEmpty(images)) {
					song.addImages(images);
				}
			}

			// GET DURATION
			song.setDurationMs(spotifySong.durationMs);

			return song;
		}
	};
}
