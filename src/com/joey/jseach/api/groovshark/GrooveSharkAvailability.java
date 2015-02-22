package com.joey.jseach.api.groovshark;

import com.joey.jseach.search.interfaces.Availibility;

class GrooveSharkAvailability implements Availibility {
	private final String deeplink;

	GrooveSharkAvailability(String deeplink) {
		this.deeplink = deeplink;
	}

	@Override
	public String getName() {
		return "GrooveShark";
	}

	@Override
	public String getDeepLink() {
		return deeplink;
	}

	@Override
	public String getIcon() {
		return null;
	}
}
