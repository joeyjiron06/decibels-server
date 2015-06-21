package test;

import com.google.gson.JsonElement;
import com.joey.jseach.App;
import com.joey.jseach.search.implementations.JSearchErrorHandler;
import com.joey.jseach.search.interfaces.MusicSearchEngine;
import com.joey.jseach.search.interfaces.MusicSearchEngineResult;
import com.joey.jseach.utils.JSU;
import com.joey.jseach.utils.Logger;
import com.joey.jseach.api.spotify.Spotify;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.JSearchException;

import org.junit.Test;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

import static org.junit.Assert.*;


import java.util.Arrays;
import java.util.List;

public class SearchTests {

	private static void log(String message, Object... args) {
		Logger.log("SearchTests", String.format(message, args));
	}

	private static final MusicSearchEngine SearchEngine = App.getInstance().getSearchEngine();

	private static final JSearchAPI jsearchAPI =  new RestAdapter.Builder()
			.setEndpoint("http://localhost:8080")
			.setErrorHandler(new JSearchErrorHandler())
			.build()
			.create(JSearchAPI.class);

	@Test
	public void searchArtists() {
		List<Artist> artistResults = null;
		try {
			artistResults = SearchEngine.searchArtist("allman brothers");

		} catch (JSearchException e) {
			log(e.toString());
		}

		assertTrue(artistResults != null && !artistResults.isEmpty());
	}

	@Test
	public void searchSong() {
		List<Song> songResults = null;
		try {
			songResults = SearchEngine.searchSong("call me");
		} catch (JSearchException e) {
			e.printStackTrace();
		}

		assertTrue(songResults != null && !songResults.isEmpty());
	}

	@Test
	public void searchAlbum() {
		List<Album> albums = null;

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
			List<Artist> artists = searchEngineResult.getArtists();
			List<Album> albums = searchEngineResult.getAlbums();
			List<Song> songs = searchEngineResult.getSongs();

			assert(!JSU.isNullOrEmpty(artists));
			assert(!JSU.isNullOrEmpty(albums));
			assert(!JSU.isNullOrEmpty(songs));
		}
	}

	@Test
	public void testAlbumSpotify() {
		Spotify spotify = new Spotify();

		try {
			List<Album> results = spotify.searchAlbum("mob rules");

			assert(!JSU.isNullOrEmpty(results));
		} catch (JSearchException e) {
			log("exception thrown " + e);
		}
	}

	/*
	* To run this test you must run a local instance of the server from the command line and uncomment the @Test annotation.
	* do:
	*
	* gradle run
	*
	* */
	//	@Test
	public void testBadTypes() {
		//all types passed in are bad
		try {
			JsonElement jsonElement = jsearchAPI.search("bl", JSU.combine(Arrays.asList("bologna", "garbage", "whatIsTHIS?"), ","));
			//we shouldnt get a response from the api. we should get an exception because its a bogus request
			assertTrue(jsonElement == null);
		} catch (JSearchException e) {
			RetrofitError retrofitError = (RetrofitError) e.getData();
			assertTrue(retrofitError.getResponse().getStatus() == 400);
		}
	}

	public interface JSearchAPI {
		@GET("/search")
		JsonElement search(
				@Query(value =  "query", encodeName = true) String query,
				@Query(value = "type", encodeName = false) String type
		) throws JSearchException;
	}
}
