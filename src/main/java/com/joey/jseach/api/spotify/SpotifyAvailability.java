package com.joey.jseach.api.spotify;

import com.joey.jseach.search.interfaces.Availibility;

class SpotifyAvailability implements Availibility {
	private final String deepLink;
	SpotifyAvailability(String deepLink) {
		this.deepLink = deepLink;
	}

	@Override
	public String getName() {
		return "Spotify";
	}

	@Override
	public String getDeepLink() {
		return deepLink;
	}

	@Override
	public String getIcon() {
		return "https://flic.kr/p/rK6o4N";
	}
}
