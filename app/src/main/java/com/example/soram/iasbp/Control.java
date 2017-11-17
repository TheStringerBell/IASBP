package com.example.soram.iasbp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Control{
    Integer mode;
    Integer status;
    String url = "http://slm.uniza.sk/~sochor/tempControl.php?mode=";
    final OkHttpClient okHttpClient = new OkHttpClient();
    public Control(Integer mode, Integer status){
        this.status = status;
        this.mode = mode;
    }
    public void test(){

        Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                Response response = okHttpClient.newCall(new Request.Builder().url(url+mode).build()).execute();
                Log.e("toto", response.toString());
                return Observable.just(response);
            }
        });
    }


}
//public class Control extends AsyncTask<String, String, String> {
//
//    String url = "http://slm.uniza.sk/~sochor/tempControl.php?mode=";
//    String url2= "http://slm.uniza.sk/~sochor/humiControl.php?mode=";
//    int mode;
//    int status;
//    Response response;
//    Response response2;
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        mode = Integer.parseInt(params[0]);
//        status = Integer.parseInt(params[1]);
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(url+mode)
//                    .build();
//            Request request2 = new Request.Builder()
//                    .url(url2+status)
//                    .build();
//            response = client.newCall(request).execute();
//            response2 = client.newCall(request2).execute();
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
