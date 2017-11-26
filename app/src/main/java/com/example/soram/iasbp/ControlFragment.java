package com.example.soram.iasbp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;



public class ControlFragment extends Fragment {
    View view;
    ArrayList<String> controlMode;
    ArrayList<String> controlStatus;
    ArrayList<String> controlLowMin;
    ArrayList<String> controlLowMax;
    ArrayList<String> controlHighMin;
    ArrayList<String> controlHighMax;
    EditText lowMin;
    EditText lowMax;
    EditText highMin;
    EditText highMax;
    EditText lowMin2;
    EditText lowMax2;
    EditText highMin2;
    EditText highMax2;
    TextView lowM;
    TextView lowMa;
    TextView highM;
    TextView highMa;
    RelativeLayout relativeLayout;
    InputMethodManager imm;
    LinearLayout ln3;

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
        lowM = view.findViewById(R.id.textView);
        lowMa = view.findViewById(R.id.textView2);
        highM = view.findViewById(R.id.textView3);
        highMa = view.findViewById(R.id.textView4);
        ln3 = view.findViewById(R.id.linearLayout3);
        relativeLayout = view.findViewById(R.id.relativeView);
        controlHighMax = getArguments().getStringArrayList("HighMax");
        controlStatus = getArguments().getStringArrayList("Status");
        controlLowMin = getArguments().getStringArrayList("LowMin");
        controlLowMax = getArguments().getStringArrayList("LowMax");
        controlHighMin = getArguments().getStringArrayList("HighMin");
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });


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
    public void hideKeyboard(View v){
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

    }

}
