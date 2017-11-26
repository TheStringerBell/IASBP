package com.example.soram.iasbp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sOram on 26. 11. 2017.
 */

public class ControlFragment extends Fragment {
    View view;
    ArrayList<String> controlMode;

    ArrayList<String> controlStatus;
    ArrayList<String> controlLowMin;
    ArrayList<String> controlLowMax;
    ArrayList<String> controlHighMin;
    ArrayList<String> controlHighMax;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.control_fragment, container, false);
        controlHighMax = getArguments().getStringArrayList("HighMax");
        controlStatus = getArguments().getStringArrayList("Status");
        controlLowMin = getArguments().getStringArrayList("LowMin");
        controlLowMax = getArguments().getStringArrayList("LowMax");
        controlHighMin = getArguments().getStringArrayList("HighMin");
        Log.e("ds", controlHighMin.toString());
        return view;
    }
}
