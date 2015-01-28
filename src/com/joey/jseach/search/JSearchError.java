package com.joey.jseach.search;

public class JSearchError {

	public enum Cause {
		NETWORK_ERROR,
		PARSE_ERROR
	}

	private final Cause cause;
	private final Object extra;


	public JSearchError(Cause cause, Object extra) {
		this.cause = cause;
		this.extra = extra;
	}


	public Cause getCause() {
		return cause;
	}

	public Object getExtra() {
		return extra;
	}
}
