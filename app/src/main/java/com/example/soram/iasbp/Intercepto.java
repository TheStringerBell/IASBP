package com.example.soram.iasbp;
import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class Intercepto implements Interceptor {
    private String credentials;
    private String mode;
    private String mode2;

    public Intercepto(String user, String password, String mode, String mode2) {
        this.credentials = Credentials.basic(user, password);
        this.mode = mode;
        this.mode2 = mode2;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authRequest = request.newBuilder()
                .header("Authorization", credentials)
                .addHeader("Mode", mode)
                .addHeader("Mode2", mode2)
                .build();
        return chain.proceed(authRequest);
    }
}




