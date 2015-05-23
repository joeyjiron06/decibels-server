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
import com.joey.jseach.search.AvailabilityWithData;
import com.joey.jseach.search.Converter;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.implementations.JSearchErrorHandler;
import com.joey.jseach.search.interfaces.Availibility;
import com.joey.jseach.search.interfaces.MusicQuerier;

import java.util.*;


public class Spotify implements MusicQuerier {

	private static final String TAG = "Spotify";

	private static final Converter<SpotifyResult.Artist, AvailabilityWithData<Artist>> ArtistConverter = new ArtistConverter();
	private static final Converter<SpotifyResult.Album, AvailabilityWithData<Album>> AlbumConverter = new AlbumConverter();
	private static final Converter<SpotifyResult.Track, AvailabilityWithData<Song>> SongConverter = new SongConverter();

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
	public List<AvailabilityWithData<Artist>> searchArtist(String artist) throws JSearchException {
		MusicQuerierSearchResult searchResult = search(artist, ARTIST_SET);
		if (searchResult != null) {
			return searchResult.getArtists();
		}
		return null;
	}

	@Override
	public List<AvailabilityWithData<Album>> searchAlbum(String album) throws JSearchException {
		MusicQuerierSearchResult searchResult = search(album, ALBUM_SET);
		if (searchResult != null) {
			return searchResult.getAlbums();
		}
		return null;
	}

	@Override
	public List<AvailabilityWithData<Song>> searchSong(String song) throws JSearchException {
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
			List<AvailabilityWithData<Artist>> artists = null;

			if (spotifyResult.artists != null) {
				artists = convertAll(spotifyResult.artists.items, ArtistConverter);
			}

			List<AvailabilityWithData<Album>> albums = null;

			if (spotifyResult.albums != null) {
				albums = convertAll(spotifyResult.albums.items, AlbumConverter);
			}

			List<AvailabilityWithData<Song>> songs = null;

			if (spotifyResult.tracks != null) {
				songs = convertAll(spotifyResult.tracks.items, SongConverter);
			}

			return new MusicQuerierSearchResult(artists, albums, songs);
		}

		return null;
	}

/* - UTILITY FUNCTIONS */

	private static <I, O> List<AvailabilityWithData<O>> convertAll(List<I> inputList, Converter<I, AvailabilityWithData<O>> converter) {
		if (inputList != null) {
			List<AvailabilityWithData<O>> result = new ArrayList<>();
			for (I input : inputList) {
				AvailabilityWithData<O> output = converter.convert(input);
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

	private static List<Image>  extractImages(List<SpotifyResult.Image> images) {
		List<Image> result = null;

		if (!JSU.isNullOrEmpty(images)) {
			result = new ArrayList<>();
			for (SpotifyResult.Image image: images) {
				result.add(new Image(image.width, image.height, image.url));
			}
		}

		return result;
	}

	private static class ArtistConverter implements Converter<SpotifyResult.Artist, AvailabilityWithData<Artist>> {
		@Override
		public AvailabilityWithData<Artist> convert(SpotifyResult.Artist spotifyArtist) {

			// CREATE ARTIST
			Artist artist = new Artist(spotifyArtist.name);

			// GET AVAILABILITY
			Availibility availibility = new SpotifyAvailability(spotifyArtist.externalUrls.spotify);

			// GET IMAGES
			List<Image> images = extractImages(spotifyArtist.images);
			if (!JSU.isNullOrEmpty(images)) {
				artist.addImages(images);
			}

			return new AvailabilityWithData<>(artist, availibility);
		}
	};

	private static class AlbumConverter implements Converter<SpotifyResult.Album, AvailabilityWithData<Album>> {

		@Override
		public AvailabilityWithData<Album> convert(SpotifyResult.Album spotifyAlbum) {

			// CREATE ALBUM
			Album album = new Album(spotifyAlbum.name);

			// GET AVAILABILITY
			Availibility availibility = new SpotifyAvailability(spotifyAlbum.externalUrls.spotify);

			// GET IMAGES
			List<Image> images = extractImages(spotifyAlbum.images);
			if (!JSU.isNullOrEmpty(images)) {
				album.addImages(images);
			}

			return new AvailabilityWithData<>(album, availibility);
		}
	};

	private static class SongConverter implements Converter<SpotifyResult.Track, AvailabilityWithData<Song>> {
		@Override
		public AvailabilityWithData<Song> convert(SpotifyResult.Track spotifySong) {

			// CREATE SONG
			Song song = new Song(spotifySong.name);

			// GET AVAILABILITY
			Availibility availibility = new SpotifyAvailability(spotifySong.externalUrls.spotify);

			// GET ARTIST NAME
			if (!JSU.isNullOrEmpty(spotifySong.artists)) {
				SpotifyResult.Artist spotifyArtist = spotifySong.artists.get(0);
				song.setArtist(spotifyArtist.name);
			}

			// GET ALBUM NAME
			if (spotifySong.album != null) {
				SpotifyResult.Album spotifyAlbum = spotifySong.album;
				song.setAlbum(spotifyAlbum.name);

				// GET IMAGES FROM ALBUM
				List<Image> images = extractImages(spotifyAlbum.images);
				if (!JSU.isNullOrEmpty(images)) {
					song.addImages(images);
				}
			}

			// GET DURATION
			song.setDurationMs(spotifySong.durationMs);

			return new AvailabilityWithData<>(song, availibility);
		}
	};
}
