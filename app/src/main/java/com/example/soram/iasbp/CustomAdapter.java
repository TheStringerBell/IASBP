package com.example.soram.iasbp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


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
