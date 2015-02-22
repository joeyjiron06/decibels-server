package com.joey.jseach.search;

public interface Converter<I, O> {
	O convert(I input);
}
