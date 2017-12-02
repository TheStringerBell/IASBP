package com.example.soram.iasbp;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface newControl{
    @GET
    retrofit2.Call<List<GetHumiData>> sqlData(@Url String url
    );
    @GET
    Call<List<GetControlData>> controlData(@Url String url
    );
    @GET
    Call<List<GetInsideData>> insideData(@Url String url
    );
    @GET
    Call<Void> control(@Url String url
    );
    @GET
    Observable<Response<ResponseBody>> obstest(@Url String url
    );

}



