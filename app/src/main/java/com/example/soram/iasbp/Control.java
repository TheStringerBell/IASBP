package com.example.soram.iasbp;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sOram on 28. 10. 2017.
 */

public class Control extends AsyncTask<String, String, String> {

    String url = "http://slm.uniza.sk/~sochor/tst.php?mode=";
    int mode;
    Response response;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        mode = Integer.parseInt(params[0]);
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url+mode)
                    .build();
            response = client.newCall(request).execute();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
