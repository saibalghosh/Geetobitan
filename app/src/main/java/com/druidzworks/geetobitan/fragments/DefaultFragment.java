package com.druidzworks.geetobitan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.druidzworks.geetobitan.R;

/**
 * Created by Saibal Ghosh on 8/26/2017.
 */

public class DefaultFragment extends Fragment {
    TextView tvMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.default_splash, null);
        tvMain = (TextView) v.findViewById(R.id.lblMain);
        tvMain.setText(R.string.app_name);
        return v;
    }
}