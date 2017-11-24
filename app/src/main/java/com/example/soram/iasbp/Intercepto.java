package com.example.soram.iasbp;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sOram on 24. 11. 2017.
 */

public class Intercepto implements Interceptor {
    private String credentials;

    public Intercepto(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authRequest);
    }
}
