package com.druidzworks.geetobitan.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.druidzworks.geetobitan.R;
import com.druidzworks.geetobitan.adapters.ExpandableListAdapter;
import com.druidzworks.geetobitan.helpers.DBHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Saibal Ghosh on 8/21/2017.
 */

public class SongListFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    HashMap<Integer,String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.list_songs, null);
        GetListData();
        listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);
        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);
        expListView.setAdapter(listAdapter);
        return v;
    }

    private void GetListData()
    {
        listDataHeader = new HashMap<>();
        listDataChild = new HashMap<>();

        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());

        listDataHeader = dbHelper.GetSongInitialHeaders();


        for(int id : listDataHeader.keySet())
        {
            List<String> songs = dbHelper.GetSongsByInitialHeaders(id);
            listDataChild.put(listDataHeader.get(id), songs);
        }
    }
}