package test;

import com.joey.jseach.App;
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

	@Test
	public void searchArtists() {
		List<AvailabilitiesList<Artist>> artistResults = null;
		try {
			artistResults = App.INSTANCE.getSearchEngine().searchArtist("allman brothers");
			for (AvailabilitiesList<Artist> artistResult : artistResults) {
				log(artistResult.toString());
			}

		} catch (JSearchException e) {
			log(e.toString());
		}

		assertTrue(artistResults != null && !artistResults.isEmpty());
	}

	@Test
	public void searchSong() {
		List<AvailabilitiesList<Song>> songResults = null;
		try {
			songResults = App.INSTANCE.getSearchEngine().searchSong("call me");
			for (AvailabilitiesList<Song> songResult : songResults) {
				log(songResult.toString());
			}
		} catch (JSearchException e) {
			e.printStackTrace();
		}

		assertTrue(songResults != null && !songResults.isEmpty());
	}

	@Test
	public void searchAlbum() {
		List<AvailabilitiesList<Album>> albums = null;

		try {
			albums = App.INSTANCE.getSearchEngine().searchAlbum("mob rules");
			for (AvailabilitiesList<Album> album : albums) {
				log(album.toString());
			}
		} catch (JSearchException e) {
			e.printStackTrace();
		}

		assertTrue(albums != null && !albums.isEmpty());
	}

	@Test
	public void testAlbumSpotify() {
		Spotify spotify = new Spotify();

		try {
			List<AvailabilityWithData<Album>> results = spotify.searchAlbum("mob rules");

			for (AvailabilityWithData<Album> albumAvailabilityWithData : results) {
				log(albumAvailabilityWithData.toString());
			}

		} catch (JSearchException e) {
			log("exception thrown " + e);
		}
	}
}
