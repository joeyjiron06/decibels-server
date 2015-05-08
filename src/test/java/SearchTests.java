package test;

import com.joey.jseach.App;
import com.joey.jseach.api.spotify.SpotifyResult;
import com.joey.jseach.search.interfaces.MusicSearchEngine;
import com.joey.jseach.search.interfaces.MusicSearchEngineResult;
import com.joey.jseach.utils.JSU;
import com.joey.jseach.utils.Logger;
import com.joey.jseach.api.spotify.Spotify;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.search.AvailabilityWithData;
import com.joey.jseach.search.JSearchException;

import org.junit.Test;
import static org.junit.Assert.*;


import java.util.List;

public class SearchTests {

	private static void log(String message) {
		Logger.log("SearchTests", message);
	}

	private static MusicSearchEngine SearchEngine = App.getInstance().getSearchEngine();

	@Test
	public void searchArtists() {
		List<AvailabilitiesList<Artist>> artistResults = null;
		try {
			artistResults = SearchEngine.searchArtist("allman brothers");

		} catch (JSearchException e) {
			log(e.toString());
		}

		assertTrue(artistResults != null && !artistResults.isEmpty());
	}

	@Test
	public void searchSong() {
		List<AvailabilitiesList<Song>> songResults = null;
		try {
			songResults = SearchEngine.searchSong("call me");
		} catch (JSearchException e) {
			e.printStackTrace();
		}

		assertTrue(songResults != null && !songResults.isEmpty());
	}

	@Test
	public void searchAlbum() {
		List<AvailabilitiesList<Album>> albums = null;

		try {
			albums = SearchEngine.searchAlbum("mob rules");
		} catch (JSearchException e) {
			e.printStackTrace();
		}

		assertTrue(albums != null && !albums.isEmpty());
	}

	@Test
	public void searchAll() {
		MusicSearchEngineResult searchEngineResult = SearchEngine.search("bl", MusicSearchEngine.SEARCH_TYPES_ALL);

		assert(searchEngineResult != null);

		if (searchEngineResult != null) {
			List<AvailabilitiesList<Artist>> artists = searchEngineResult.getArtists();
			List<AvailabilitiesList<Album>> albums = searchEngineResult.getAlbums();
			List<AvailabilitiesList<Song>> songs = searchEngineResult.getSongs();

			assert(!JSU.isNullOrEmpty(artists));
			assert(!JSU.isNullOrEmpty(albums));
			assert(!JSU.isNullOrEmpty(songs));
		}
	}

	@Test
	public void testAlbumSpotify() {
		Spotify spotify = new Spotify();

		try {
			List<AvailabilityWithData<Album>> results = spotify.searchAlbum("mob rules");

			assert(!JSU.isNullOrEmpty(results));
		} catch (JSearchException e) {
			log("exception thrown " + e);
		}
	}
}
