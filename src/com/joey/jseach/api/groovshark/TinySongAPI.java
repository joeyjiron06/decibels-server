package com.joey.jseach.api.groovshark;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.ArrayList;

public interface TinySongAPI {
	@GET("/s/{text}?format=json")
	void search(@Path("text") String text, Callback<ArrayList<TinySongItem>> callback);
}
