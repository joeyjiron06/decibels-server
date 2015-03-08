package com.joey.jseach.search.implementations;

import com.joey.jseach.search.JSearchException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class JSearchErrorHandler implements ErrorHandler {

	@Override
	public Throwable handleError(RetrofitError error) {
		RetrofitError.Kind kind = error.getKind();

		switch (kind) {
			case NETWORK:
			case HTTP:
			case UNEXPECTED:
				return new JSearchException(JSearchException.Reason.Network, error.getResponse());
			case CONVERSION:
				return new JSearchException(JSearchException.Reason.Parse, error.getResponse());
		}

		return new IllegalStateException("retrofit error should be handled");
	}
}
