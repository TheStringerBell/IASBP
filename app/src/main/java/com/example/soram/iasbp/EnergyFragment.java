package com.example.soram.iasbp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class EnergyFragment extends Fragment {
    View view;
    ArrayList<String> times;
    ArrayList<String> values;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.energy_fragment, container, false);

        times = getArguments().getStringArrayList("EnergyTime");
        values = getArguments().getStringArrayList("EnergyValue");

        return view;

    }
}
