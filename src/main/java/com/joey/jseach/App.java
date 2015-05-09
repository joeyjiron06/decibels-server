package com.joey.jseach;


import com.google.gson.JsonObject;
import com.joey.jseach.search.JSearchException;
import com.joey.jseach.search.SearchType;
import com.joey.jseach.search.implementations.JMusicSearchEngine;
import com.joey.jseach.search.interfaces.MusicSearchEngine;
import com.joey.jseach.utils.JSU;

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

/* - M A I N */

	public static void main(String[] args) {
		final MusicSearchEngine SearchEngine = App.getInstance().getSearchEngine();

		port(8080);


		get("/search", (request, response) -> {
			QueryParamsMap params = request.queryMap();
			String types = params.get("type").value();
			String query = params.get("query").value();

			//check for query
			if (query == null) {
				halt(400, "Invalid request. You must specify a 'query' parameter.");
			}

			List<SearchType> searchTypes = new ArrayList<>();

			if (JSU.isNullOrEmpty(types)) {
				//default to all search types
				searchTypes.addAll(Arrays.asList(SearchType.values()));
			} else {
				//split types and add to set
				String[] typesList = JSU.safeSplit(types);
				List<String> badTypes = null;

				for (String typeItem : typesList) {
					SearchType searchType = SearchType.fromApiValue(typeItem);
					if (searchType != null) {
						searchTypes.add(searchType);
					} else {
						//user entered a bad type. add it to the list
						if (badTypes == null) {
							badTypes = new ArrayList<>();
						}

						if (!badTypes.contains(typeItem)) {
							badTypes.add(typeItem);
						}
					}
				}

				if (!JSU.isNullOrEmpty(badTypes)) {
					//user entered bad types. return a bad request
					halt(400, "Invalid request. Bad type(s) " + JSU.combine(badTypes, ","));
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
