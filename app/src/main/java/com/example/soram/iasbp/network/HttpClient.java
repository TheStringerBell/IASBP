package com.example.soram.iasbp.network;

import com.example.soram.iasbp.utils.DecryptInterceptor;
import com.example.soram.iasbp.utils.DefaultInterceptor;
import com.example.soram.iasbp.utils.Intercepto;

import okhttp3.OkHttpClient;

public class HttpClient {
    String username;
    String password;
    String mode;
    String mode2;

    public HttpClient(String username, String password){
        this.username = username;
        this.password = password;

    }
    public okhttp3.OkHttpClient getClient(){

        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Intercepto(username, password, mode, mode2))
                .addInterceptor(new DecryptInterceptor())
                .build();

    }
    public OkHttpClient getControlClient(String mode, String mode2){
        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Intercepto(username, password, mode, mode2))
                .build();

    }
    public OkHttpClient getDefaultClient(){
        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new DefaultInterceptor(username, password))
                .addInterceptor(new DecryptInterceptor())
                .build();

    }

}
