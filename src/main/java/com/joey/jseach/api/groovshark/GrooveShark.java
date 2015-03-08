package com.joey.jseach.api.groovshark;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


public class GrooveShark  {
	public static final String API_KEY = "";//TODO read from file

	private final TinySongAPI tinySongAPI;

	public GrooveShark() {
		tinySongAPI = new RestAdapter.Builder()
				.setEndpoint("http://tinysong.com")
				.setRequestInterceptor(new RequestInterceptor() {
					@Override
					public void intercept(RequestFacade requestFacade) {
						requestFacade.addQueryParam("key", API_KEY);
					}
				})
				.build()
				.create(TinySongAPI.class);

	}


	//TODO implement music querier
}
