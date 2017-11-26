package com.example.soram.iasbp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    TextView lowMin;
    TextView lowMax;
    TextView highMin;
    TextView highMax;
    TextView lowMin2;
    TextView lowMax2;
    TextView highMin2;
    TextView highMax2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.control_fragment, container, false);
        lowMax = view.findViewById(R.id.editText2);
        lowMin = view.findViewById(R.id.editText);
        highMax = view.findViewById(R.id.editText4);
        highMin = view.findViewById(R.id.editText3);
        lowMax2 = view.findViewById(R.id.editText5);
        lowMin2 = view.findViewById(R.id.editText6);
        highMin2 = view.findViewById(R.id.editText7);
        highMax2 = view.findViewById(R.id.editText8);
        controlHighMax = getArguments().getStringArrayList("HighMax");
        controlStatus = getArguments().getStringArrayList("Status");
        controlLowMin = getArguments().getStringArrayList("LowMin");
        controlLowMax = getArguments().getStringArrayList("LowMax");
        controlHighMin = getArguments().getStringArrayList("HighMin");

        lowMin.setText(controlLowMin.get(0));
        lowMax.setText(controlLowMax.get(0));
        highMin.setText(controlHighMin.get(0));
        highMax.setText(controlHighMax.get(0));
        lowMin2.setText(controlLowMin.get(1));
        lowMax2.setText(controlLowMax.get(1));
        highMin2.setText(controlHighMin.get(1));
        highMax2.setText(controlHighMax.get(1));
        return view;
    }
}
