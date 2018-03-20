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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        try {
            String ok = new AESDecrypt().Decrypt(response.body().string());
//            Log.d("Decrypted", ok);
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), ok)).build();

        } catch (Exception o) {
            o.printStackTrace();
            return response;

        }
    }
}
