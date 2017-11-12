package com.example.soram.iasbp;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {
    ActionBar actionBar;
    ArrayList<String> arrayTime;
    ArrayList<String> arrayValue;
    List<Map.Entry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        arrayTime = getIntent().getStringArrayListExtra("Values");
        arrayValue = getIntent().getStringArrayListExtra("Time");

        for (int i = 0; i < arrayValue.size()-1; i++){


        }
    }
}
