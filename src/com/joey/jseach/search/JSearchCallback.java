package com.joey.jseach.search;



public interface JSearchCallback<T> {
	/**
	 * Called when loading starts. May not be called if retrieved from cache.
	 *
	 * */
	void onPreload();

	/**
	 * Called when the object was successfully retrieved.
	 * */
	void onSuccess(T data);


	/**
	 * Called when failed to retrieve the data.
	 * */
	void onFailure(JSearchError error);
}
