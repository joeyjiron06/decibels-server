package com.joey.jseach.search;

public class JSearchException extends RuntimeException {

	public enum Reason {
		Network,
		Parse,
		Unexpected
	}

	private final Reason reason;
	private final Object data;

	public JSearchException(Reason reason, Object data) {
		this.reason = reason;
		this.data = data;
	}

	public Reason getReason() {
		return reason;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return String.format("reason : %s data: %s", reason, data);
	}
}
