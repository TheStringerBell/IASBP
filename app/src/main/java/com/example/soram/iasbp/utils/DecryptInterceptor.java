package com.example.soram.iasbp.utils;

import com.example.soram.iasbp.utils.AESDecrypt;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DecryptInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        try {
            String decryptedResponse= new AESDecrypt().Decrypt(response.body().string());
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), decryptedResponse)).build();

        } catch (Exception o) {
            o.printStackTrace();
            return response;

        }
    }
}
