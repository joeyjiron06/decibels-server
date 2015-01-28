package com.joey.jseach.debug.ui;

import com.joey.jseach.core.Album;
import com.joey.jseach.core.Artist;
import com.joey.jseach.core.Song;
import com.joey.jseach.search.SearchItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by jjiron on 1/27/15.
 */
public class DebugView extends JFrame {
    private JTextField searchTextField;
    private JList resultsListView;
    private JPanel rootPanel;

    public DebugView() {
        super("JSearch Debug View");
        init();
        setSize(700, 400);
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
        resultsListView.setCellRenderer(new ResultCellRenderer());
    }

    public void addSearchListner(ActionListener listener) {
        searchTextField.addActionListener(listener);
    }

    public String getSearchText() {
        return searchTextField.getText();
    }

    public void setListData(Object[] data) {
        resultsListView.setListData(data);
    }



    private static final class ResultCellRenderer implements ListCellRenderer<SearchItem<?>> {

        @Override
        public Component getListCellRendererComponent(JList<? extends SearchItem<?>> list, SearchItem<?> value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel();

            Object data = value.getData();


            if (data instanceof Artist) {
                label.setText("Artist : " + ((Artist)data).getName());
            } else if (data instanceof Song) {
                label.setText("Song : " + ((Song)data).getName());

            } else if (data instanceof Album) {
                label.setText("Album : " + ((Album)data).getName());

            }


            return label;
        }
    }




}
