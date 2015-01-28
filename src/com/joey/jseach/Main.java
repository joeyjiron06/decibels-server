package com.joey.jseach;


import com.joey.jseach.core.Artist;
import com.joey.jseach.debug.ui.DebugView;
import com.joey.jseach.search.JSearchCallback;
import com.joey.jseach.search.JSearchError;
import com.joey.jseach.search.SearchItem;
import com.joey.jseach.search.impl.JMusicSearchEngine;
import com.joey.jseach.search.inter.MusicSearchEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {

	private static final String TAG = "Main";

	private final MusicSearchEngine musicSearchEngine;
	private final DebugView debugView;

	private Main() {
		musicSearchEngine = new JMusicSearchEngine();
		debugView = new DebugView();
		debugView.addSearchListner(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search(debugView.getSearchText());
			}
		});
	}

	private void search(String query) {
		musicSearchEngine.searchArtist(query, new JSearchCallback<List<SearchItem<Artist>>>() {
			@Override
			public void onPreload() {

			}

			@Override
			public void onSuccess(List<SearchItem<Artist>> searchItems) {
				debugView.setListData(searchItems.toArray());
			}

			@Override
			public void onFailure(JSearchError error) {

			}
		});
	}


	public static void main(String[] args) {
		new Main();
	}
}
