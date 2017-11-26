package com.example.soram.iasbp;

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

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Intercepto(username, password, mode, mode2))
                .build();
        return client;
    }

}
