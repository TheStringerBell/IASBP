package com.example.soram.iasbp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sOram on 11. 11. 2017.
 */

public class MainFragment extends Fragment{
    View view;
    TextView textHumi;
    TextView textTemp;
    ArrayList<String> values;
    ArrayList<String> time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.main_fragment, container, false);
        textHumi = (TextView) view.findViewById(R.id.textHumi);
        textTemp = (TextView) view.findViewById(R.id.textTemp);
        time = getArguments().getStringArrayList("Time");
        values = getArguments().getStringArrayList("Values");
        textHumi.setText(values.get(values.size()-1));
        textTemp.setText(values.get(values.size()-10));
        return view;
    }
}
