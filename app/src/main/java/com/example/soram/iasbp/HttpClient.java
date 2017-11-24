package com.example.soram.iasbp;

/**
 * Created by sOram on 24. 11. 2017.
 */

public class HttpClient {
    String username;
    String password;
    public HttpClient(String username, String password){
        this.username = username;
        this.password = password;

    }
    public okhttp3.OkHttpClient getClient(){
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Intercepto(username, password))
                .build();
        return client;
    }

}
