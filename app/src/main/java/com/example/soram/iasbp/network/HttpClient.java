package com.example.soram.iasbp.network;

import com.example.soram.iasbp.utils.DecryptInterceptor;
import com.example.soram.iasbp.utils.Intercepto;

import okhttp3.OkHttpClient;

public class HttpClient {
    String username;
    String password;
    String mode;
    String mode2;

    public HttpClient(String username, String password, String mode, String mode2){
        this.username = username;
        this.password = password;
        this.mode = mode;
        this.mode2 = mode2;
    }
    public okhttp3.OkHttpClient getClient(){

        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Intercepto(username, password, mode, mode2))
                .addInterceptor(new DecryptInterceptor())
                .build();

    }
    public OkHttpClient getControlClient(){
        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Intercepto(username, password, mode, mode2))
                .build();

    }

}
