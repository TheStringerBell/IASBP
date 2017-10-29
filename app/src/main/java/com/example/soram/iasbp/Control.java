package com.example.soram.iasbp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sOram on 28. 10. 2017.
 */

public class Control extends AsyncTask<String, String, String> {

    String url = "http://slm.uniza.sk/~sochor/tempControl.php?mode=";
    String url2= "http://slm.uniza.sk/~sochor/humiControl.php?mode=";
    int mode;
    int status;
    Response response;
    Response response2;
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
        status = Integer.parseInt(params[1]);
        Log.e("Sending", " " + mode +" " +status);


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url+mode)
                    .build();
            Request request2 = new Request.Builder()
                    .url(url2+status)
                    .build();
            response = client.newCall(request).execute();
            response2 = client.newCall(request2).execute();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
