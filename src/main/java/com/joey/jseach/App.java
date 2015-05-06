package com.joey.jseach;


import com.google.gson.JsonObject;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.search.implementations.JMusicSearchEngine;
import com.joey.jseach.search.interfaces.MusicSearchEngine;
import com.joey.jseach.utils.Strings;

import java.util.*;

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
		final MusicSearchEngine SearchEngine = App.getInstance().getSearchEngine();

		port(8080);


		get("/search", (request, response) -> {
			QueryParamsMap params = request.queryMap();
			String type = params.get("type").value();
			String query = params.get("query").value();

			//check for query
			if (query == null) {
				halt(400, "Invalid request. You must specify a 'query' parameter.");
			}

			//getInstance search types for search engine
			Set<SearchType> searchTypes = new HashSet<SearchType>();

			if (Strings.isNullOrEmpty(type)) {
				//default to all search types
				searchTypes.addAll(Arrays.asList(SearchType.values()));
			} else {
				//split types and add to set
				String[] typesList = Strings.safeSplit(type);
				for (String typeItem : typesList) {
					SearchType searchType = SearchType.fromApiValue(typeItem);
					if (searchType != null) {
						searchTypes.add(searchType);
					}
				}
			}

			try {
				JsonObject json;

				if (!searchTypes.isEmpty()) {
					//query the search engine
					json = SearchEngine.search(query, searchTypes).toJson();
				} else {
					//empty json object
					json = new JsonObject();
				}

				response.type("application/json");
				return json;
			} catch (JSearchException e) {
				//TODO determing what happens here
			}


			return null;
		});

		after((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
		});
	}
}
