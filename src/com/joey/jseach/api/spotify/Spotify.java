package com.joey.jseach.api.spotify;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.inter.Availibility;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.joey.jseach.search.JSearchCallback;
import com.joey.jseach.search.SearchItem;
import com.joey.jseach.search.inter.MusicQuerier;

import java.util.ArrayList;
import java.util.List;

public class Spotify implements MusicQuerier {

    private static final String TAG = "Spotify";

    private final SpofityAPI spofityAPI;


    public Spotify() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://com.joey.jseach.api.spotify.com")
                .build();

        spofityAPI = restAdapter.create(SpofityAPI.class);
    }

    @Override
    public void searchArtist(String query, final JSearchCallback<List<SearchItem<Artist>>> cb) {
        spofityAPI.search(query, SpofityAPI.SearchType.Artist.apiValue(), new Callback<ArrayList<SpotifyItem>>() {
            @Override
            public void success(ArrayList<SpotifyItem> spotifyItems, Response response) {
                if (spotifyItems != null) {

                    List<SearchItem<Artist>> results = new ArrayList<>();

                    for (SpotifyItem spotifyItem : spotifyItems) {
                        Artist artist = new Artist("todo artistname");
                        results.add(new SearchItem<Artist>(artist, null));
                    }

                } else {
                    failure(RetrofitError.unexpectedError(response.getUrl(), new IllegalArgumentException("no items!")));
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });

    }

    @Override
    public void searchAlbum(String album, JSearchCallback<List<SearchItem<Album>>> cb) {

    }

    @Override
    public void searchSong(String song, JSearchCallback<List<SearchItem<Song>>> cb) {

    }

    private static class SpotifyAvailability implements Availibility {

        @Override
        public String getName() {
            return "Spotify";
        }

        @Override
        public String getDeepLink() {
            return null;
        }

        @Override
        public String getIcon() {
            return null;
        }
    }
}
