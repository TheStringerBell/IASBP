package com.example.soram.iasbp;

import android.util.Log;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;



public class Intercepto2 implements Interceptor {
    private String credentials;
    private String mode;
    private String mode2;


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.d("dsa", response.body().toString());

        try {
            String str = new ApiKeys().encryptToken(response.body().toString());
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), str)).build();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException | NoSuchProviderException o) {
            return response;

        }
    }
}
