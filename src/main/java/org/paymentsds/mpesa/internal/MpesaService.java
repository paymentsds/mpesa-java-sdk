package org.paymentsds.mpesa.internal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @GET("/ipg/v1x/queryTransactionStatus/")
    Call<MpesaResponse> query(
            @Header("Authorization") String authorization,
            @Query("input_QueryReference") String input_QueryReference,
            @Query("input_ThirdPartyReference") String input_ThirdPartyReference,
            @Query("input_ServiceProviderCode") String input_ServiceProviderCode);

    @Headers({
            "Content-Type: application/json",
            "Origin: developer.mpesa.vm.co.mz"
    })
    @PUT("/ipg/v1x/reversal/")
    Call<MpesaResponse> reversal(
            @Header("Authorization") String authorization,
            @Body MpesaRequest request);
}