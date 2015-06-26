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
				return new JSearchException(JSearchException.Reason.Network, error);
			case CONVERSION:
				return new JSearchException(JSearchException.Reason.Parse, error);
			case UNEXPECTED:
				return new JSearchException(JSearchException.Reason.Unexpected, error);
		}

		throw new IllegalStateException("retrofit error should be handled");
	}
}
