package com.joey.jseach.search;

import com.joey.jseach.search.inter.Availibility;

import java.util.Collections;
import java.util.List;

public class SearchItem<T> {

	private final T data;
	private final List<Availibility> availibilities;


	public SearchItem(T data, List<Availibility> availibilities1) {
		this.data = data;
		this.availibilities = availibilities1;
	}

	public T getData() {
		return data;
	}

	public List<Availibility> getAvailibilities() {
		return Collections.unmodifiableList(availibilities);
	}
}
