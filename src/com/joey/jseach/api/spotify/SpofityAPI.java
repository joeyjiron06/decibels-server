package com.joey.jseach.api.spotify;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.ArrayList;

public interface SpofityAPI {

    public enum SearchType {
        Album("album"),
        Artist("artist"),
        Track("track"),
        Playlist("playlist");

        private final String apiValue;

        private SearchType(String apiValue) {
            this.apiValue = apiValue;
        }

        public String apiValue() {
            return this.apiValue;
        }
    }

    /**
     * @param query the query string
     * */
    @GET("/v1/com.joey.jseach.search")
    void search(@Query(value = "q", encodeName = true) String query, @Query("type") String type, Callback<ArrayList<SpotifyItem>> cb);
}
