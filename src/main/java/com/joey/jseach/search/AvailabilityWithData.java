package com.joey.jseach.search;


import com.joey.jseach.search.interfaces.Availibility;

public class AvailabilityWithData<T> {

	private final T data;
	private final Availibility availibility;

	public AvailabilityWithData(T data, Availibility availibility) {
		this.data = data;
		this.availibility = availibility;
	}

	public T getData() {
		return data;
	}

	public Availibility getAvailibility() {
		return availibility;
	}

	@Override
	public String toString() {
		return data  + "\t" + availibility.toPrettyString();
	}
}
