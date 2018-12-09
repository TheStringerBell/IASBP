package com.example.soram.iasbp.utils;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import com.example.soram.iasbp.R;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.soram.iasbp.pojo.ListModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CustomAdapter extends ArrayAdapter<ListModel> {

    int layoutRes;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<ListModel> listModel) {
        super(context, resource, listModel);
        this.layoutRes = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutRes, null);
        }
        ListModel listModel = getItem(position);

        if (listModel != null){
            TextView host = view.findViewById(R.id.rowTextView4);
            TextView ip = view.findViewById(R.id.rowTextView5);

            if (host != null){
                host.setText(listModel.getHostname());
            }
            if (ip != null){
                ip.setText(listModel.getIpAddress());
            }

        }
        return view;
    }
}
