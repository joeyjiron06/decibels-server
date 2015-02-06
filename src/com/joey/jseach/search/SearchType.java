package com.joey.jseach.search;


import com.joey.jseach.search.interfaces.MusicQuerier;
import com.joey.jseach.core.*;

import java.util.List;

public enum SearchType {
	Artist("artist") {
		@Override
		public List<AvailabilityWithData<Artist>> apply(String query, MusicQuerier querier) throws JSearchException {
			return querier.searchArtist(query);
		}
	},
	Album("album") {
		@Override
		public List<AvailabilityWithData<Album>> apply(String query, MusicQuerier querier) throws JSearchException {
			return querier.searchAlbum(query);
		}
	},
	Song("song") {
		@Override
		public List<AvailabilityWithData<Song>> apply(String query, MusicQuerier querier) throws JSearchException {
				return querier.searchSong(query);
			}
	};

	private final String apiValue;

	SearchType(String apiValue) {
		this.apiValue = apiValue;
	}


	public abstract<T> List<AvailabilityWithData<T>> apply(String query, MusicQuerier querier) throws JSearchException;



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
