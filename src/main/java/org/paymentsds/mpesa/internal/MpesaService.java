package org.paymentsds.mpesa.internal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface MpesaService {

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @POST("/ipg/v1x/{operation}")
    Call<MpesaResponse> post(
            @Header("Authorization") String authorization,
            @Path("operation") String operation,
            @Body MpesaRequest request);

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @GET("/ipg/v1x/{operation}")
    Call<MpesaResponse> get(
            @Header("Authorization") String authorization,
            @Path("operation") String operation,
            @QueryMap Map<String, String> params);

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @PUT("/ipg/v1x/{operation}")
    Call<MpesaResponse> put(
            @Header("Authorization") String authorization,
            @Path("operation") String operation,
            @Body MpesaRequest request);
}