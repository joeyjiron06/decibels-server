package com.joey.jseach;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.network.JsonSerializable;
import com.joey.jseach.search.AvailabilitiesList;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.search.implementations.JMusicSearchEngine;
import com.joey.jseach.search.interfaces.MusicSearchEngine;
import com.joey.jseach.utils.JsonUtils;

import java.util.List;

import spark.QueryParamsMap;

import static spark.Spark.*;

/**
 * Endpoint
 *
 * /search
 *
 * params -
 *
 * type : artist, album, song
 *
 * query : query text
 *
 * Example
 *
 * http://localhost:8080/search?query=Black+Sabbath&type=artist
 *
 * response
 *
 * [{
 *    "name" : "black sabbath",
 *    "image" : "http://...",
 *    "availabilities" : [
 *      {
 *          "deep_link" : "http://...",
 *          "img" : "http://...",
 *          "name" : "Spotify"
 *      },
 *      ...
 *    ]
 * },
 * ....]
 *
 * */
public class App {
	private static final String TAG = "Main";

	public static final App INSTANCE = new App();

	private final MusicSearchEngine musicSearchEngine;

	private App() {
		musicSearchEngine = new JMusicSearchEngine();
	}

	public static App getInstance() {
		return INSTANCE;
	}

	public MusicSearchEngine getSearchEngine() {
		return musicSearchEngine;
	}



	public static void main(String[] args) {
		final App app = App.INSTANCE;

		port(8080);


		get("/search", (request, response) -> {
			QueryParamsMap params = request.queryMap();
			String type = params.get("type").value();
			String query = params.get("query").value();

			SearchType searchType = SearchType.fromApiValue(type);

			if (searchType == null || query == null) {
				halt(400, "Invalid request " + request.queryString());
			}


			JsonArray resultsArray = null;

			switch (searchType) {
				case Artist:
					List<AvailabilitiesList<Artist>> artists = app.getSearchEngine().searchArtist(query);
					resultsArray = JsonUtils.toJson(artists);
					break;

				case Album:
					List<AvailabilitiesList<Album>> albums = app.getSearchEngine().searchAlbum(query);
					resultsArray = JsonUtils.toJson(albums);
					break;

				case Song:
					List<AvailabilitiesList<Song>> songs = app.getSearchEngine().searchSong(query);
					resultsArray = JsonUtils.toJson(songs);
					break;
			}

			if (resultsArray != null) {
				response.type("application/json");
				JsonObject result = new JsonObject();
				result.add("results", resultsArray);
				return result;
			}


			return null;
		});

		after((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
		});
	}
}
