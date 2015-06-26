package com.joey.jseach.search;


public enum SearchType {
	Artist("artist"),
	Album("album"),
	Song("song");

	private final String apiValue;

	SearchType(String apiValue) {
		this.apiValue = apiValue;
	}


	public static SearchType fromApiValue(String apiValue) {
		if (apiValue != null) {
			for (SearchType searchType : SearchType.values()) {
				if (searchType.apiValue.equals(apiValue)) {
					return searchType;
				}
			}
		}
		return null;
	}
}
