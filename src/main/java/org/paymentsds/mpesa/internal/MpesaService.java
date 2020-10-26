package org.paymentsds.mpesa.internal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MpesaService {

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @POST("/ipg/v1x/c2bPayment/singleStage/")
    Call<MpesaResponse> c2b(
            @Header("Authorization") String authorization,
            @Body MpesaRequest request);

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @POST("/ipg/v1x/b2cPayment/")
    Call<MpesaResponse> b2c(
            @Header("Authorization") String authorization,
            @Body MpesaRequest request);

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @POST("/ipg/v1x/b2bPayment/")
    Call<MpesaResponse> b2b(
            @Header("Authorization") String authorization,
            @Body MpesaRequest request);
}