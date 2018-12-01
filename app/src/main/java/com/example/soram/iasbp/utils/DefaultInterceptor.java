package com.example.soram.iasbp.utils;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class DefaultInterceptor implements Interceptor {

    private String credentials;


    public DefaultInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);

    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request authRequest = request.newBuilder()
                .header("Authorization", credentials)
                .build();
        return chain.proceed(authRequest);
    }
}

