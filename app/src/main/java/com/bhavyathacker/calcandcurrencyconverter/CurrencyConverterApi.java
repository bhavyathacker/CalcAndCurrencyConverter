package com.bhavyathacker.calcandcurrencyconverter;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CurrencyConverterApi {

    @GET("convert")
    Call<ResponseBody> getConvertedData(@QueryMap Map<String, String> parameters);

}
