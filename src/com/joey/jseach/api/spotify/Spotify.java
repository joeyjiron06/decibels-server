package com.joey.jseach.api.spotify;

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

import java.util.ArrayList;
import java.util.List;

import static com.joey.jseach.api.spotify.SpotifyResultAlbum.SpotifyAlbum;
import static com.joey.jseach.api.spotify.SpotifyResultArtist.SpotifyArtist;
import static com.joey.jseach.api.spotify.SpotifyResultSong.SpotifySong;


public class Spotify implements MusicQuerier {

	private static final String TAG = "Spotify";

	private static final Converter<SpotifyArtist, AvailabilityWithData<Artist>> ArtistConverter = new ArtistConverter();
	private static final Converter<SpotifyAlbum, AvailabilityWithData<Album>> AlbumConverter = new AlbumConverter();
	private static final Converter<SpotifySong, AvailabilityWithData<Song>> SongConverter = new SongConverter();

	private final SpotifyAPI spotifyAPI;

	public Spotify() {
		spotifyAPI = new RestAdapter.Builder()
				.setEndpoint("https://api.spotify.com")
				.setErrorHandler(new JSearchErrorHandler())
				.build()
				.create(SpotifyAPI.class);
	}


	@Override
	public List<AvailabilityWithData<Artist>> searchArtist(String artist) throws JSearchException {
		SpotifyResultArtist spotifyResult = spotifyAPI.searchArtist(artist);
		List<AvailabilityWithData<Artist>> results = new ArrayList<>(spotifyResult.artists.items.size());
		for (SpotifyArtist spotifyArtist : spotifyResult.artists.items) {
			results.add(ArtistConverter.convert(spotifyArtist));
		}
		return results;
	}

	@Override
	public List<AvailabilityWithData<Album>> searchAlbum(String album) throws JSearchException {
		SpotifyResultAlbum resultAlbum = spotifyAPI.searchAlbum(album);
		List<AvailabilityWithData<Album>> results = new ArrayList<>(resultAlbum.albums.items.size());
		for (SpotifyAlbum spotifyAlbum : resultAlbum.albums.items) {
			results.add(AlbumConverter.convert(spotifyAlbum));
		}
		return results;
	}

	@Override
	public List<AvailabilityWithData<Song>> searchSong(String song) throws JSearchException {
		SpotifyResultSong spotifyResult = spotifyAPI.searchSong(song);
		List<AvailabilityWithData<Song>> results = new ArrayList<>();
		for (SpotifySong spotifySong : spotifyResult.tracks.items) {
			results.add(SongConverter.convert(spotifySong));
		}
		return results;
	}


	private static class ArtistConverter implements Converter<SpotifyArtist, AvailabilityWithData<Artist>> {
		@Override
		public AvailabilityWithData<Artist> convert(SpotifyArtist spotifyArtist) {
			Artist artist = new Artist(spotifyArtist.name, null);
			Availibility availibility = new SpotifyAvailability("http://open.spotify.com/artist/"+spotifyArtist.id);
			return new AvailabilityWithData<>(artist, availibility);
		}
	};

	private static class AlbumConverter implements Converter<SpotifyAlbum, AvailabilityWithData<Album>> {

		@Override
		public AvailabilityWithData<Album> convert(SpotifyAlbum input) {
			Availibility availibility = new SpotifyAvailability(input.externalUrls.spotify);

			String imgUrl = null;
			if (input.images != null && !input.images.isEmpty()) {
				imgUrl = input.images.get(0).url;
			}
			Album data = new Album(input.name, imgUrl);

			return new AvailabilityWithData<>(data, availibility);
		}
	};

	private static class SongConverter implements Converter<SpotifySong, AvailabilityWithData<Song>> {
		@Override
		public AvailabilityWithData<Song> convert(SpotifySong input) {
			Availibility availibility = new SpotifyAvailability(input.externalUrls.spotify);
			String artistName = null;
			if (input.artists != null && !input.artists.isEmpty()) {
				artistName = input.artists.get(0).name;
			}
			Song song = new Song(input.name, input.album.name, artistName, null);
			return new AvailabilityWithData<>(song, availibility);
		}
	};
}
