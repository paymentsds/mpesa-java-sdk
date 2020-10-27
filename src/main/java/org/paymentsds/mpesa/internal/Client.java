package org.paymentsds.mpesa.internal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.paymentsds.mpesa.Callback;
import org.paymentsds.mpesa.Request;
import org.paymentsds.mpesa.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class Client implements retrofit2.Callback<MpesaResponse> {
    private final String apiKey;
    private final String publicKey;
    private final String serviceProviderCode;
    private final String initiatorIdentifier;
    private final String host;
    private final String securityCredential;

    private final String authorizationToken;
    private Callback callback;

    // Constants
    private static final int PORT_C2B = 18352;
    private static final int PORT_B2B = 18349;
    private static final int PORT_B2C = 18345;
    private static final int PORT_QUERY = 18353;
    private static final int PORT_REVERSAL = 18354;

    private static final String PATH_C2B = "c2bPayment/singleStage/";
    private static final String PATH_B2B = "b2bPayment/";
    private static final String PATH_B2C = "b2cPayment/";
    private static final String PATH_QUERY = "queryTransactionStatus/";
    private static final String PATH_REVERSAL = "reversal/";

    public Client(
            String apiKey,
            String publicKey,
            String serviceProviderCode,
            String initiatorIdentifier,
            String host,
            String securityCredential) {
        this.apiKey = apiKey;
        this.publicKey = publicKey;
        this.serviceProviderCode = serviceProviderCode;
        this.initiatorIdentifier = initiatorIdentifier;
        this.host = host;
        this.securityCredential = securityCredential;
        this.authorizationToken = generateAuthorizationToken();
    }

    public Response receive(Request request) throws IOException {
        if (request.getFrom() == null) {
            throw new IllegalArgumentException("Request must contain a 'from' field to receive money.");
        }
        MpesaService service = getService(PORT_C2B);
        MpesaRequest mpesaRequest = MpesaRequest.fromC2BRequest(request, serviceProviderCode);
        retrofit2.Response<MpesaResponse> response =
                service.post(authorizationToken, PATH_C2B, mpesaRequest).execute();
        return parseHttpResponse(response);
    }

    public void receive(Request request, Callback callback) {
        if (request.getFrom() == null) {
            throw new IllegalArgumentException("Request must contain a 'from' field to receive money.");
        }
        this.callback = callback;
        MpesaService service = getService(PORT_C2B);
        MpesaRequest mpesaRequest = MpesaRequest.fromC2BRequest(request, serviceProviderCode);
        service.post(authorizationToken, PATH_C2B, mpesaRequest).enqueue(this);
    }

    public Response send(Request request) throws IOException {
        if (request.getTo() == null) {
            throw new IllegalArgumentException("Request must contain a 'to' field to send money.");
        }
        MpesaService service;
        retrofit2.Response<MpesaResponse> response;
        MpesaRequest mpesaRequest;
        // TODO(rosario): Improve this check once we implement regex to validate msisdn
        if (request.getTo().startsWith("258")) {
            service = getService(PORT_B2C);
            mpesaRequest = MpesaRequest.fromB2CRequest(request, serviceProviderCode);
            response = service.post(authorizationToken, PATH_B2C, mpesaRequest).execute();
        } else {
            service = getService(PORT_B2B);
            mpesaRequest = MpesaRequest.fromB2BRequest(request, serviceProviderCode);
            response = service.post(authorizationToken, PATH_B2B, mpesaRequest).execute();
        }
        return parseHttpResponse(response);
    }

    public void send(Request request, Callback callback) {
        if (request.getTo() == null) {
            throw new IllegalArgumentException("Request must contain a 'to' field to send money.");
        }
        this.callback = callback;
        MpesaRequest mpesaRequest;
        MpesaService service;
        // TODO(rosario): Improve this check once we implement regex to validate msisdn
        if (request.getTo().startsWith("258")) {
            mpesaRequest = MpesaRequest.fromB2CRequest(request, serviceProviderCode);
            service = getService(PORT_B2C);
            service.post(authorizationToken, PATH_B2C, mpesaRequest).enqueue(this);
        } else {
            mpesaRequest = MpesaRequest.fromB2BRequest(request, serviceProviderCode);
            service = getService(PORT_B2B);
            service.post(authorizationToken, PATH_B2B, mpesaRequest).enqueue(this);
        }
    }

    public Response query(Request request) throws IOException {
        MpesaService service = getService(PORT_QUERY);
        HashMap<String, String> params = new HashMap<>();
        params.put("input_QueryReference", request.getReference());
        params.put("input_ThirdPartyReference", request.getTransaction());
        params.put("input_ServiceProviderCode", serviceProviderCode);
        retrofit2.Response<MpesaResponse> response = service
                .get(authorizationToken, PATH_QUERY, params)
                .execute();
        return parseHttpResponse(response);
    }

    public void query(Request request, Callback callback) {
        MpesaService service = getService(PORT_QUERY);
        HashMap<String, String> params = new HashMap<>();
        params.put("input_QueryReference", request.getReference());
        params.put("input_ThirdPartyReference", request.getTransaction());
        params.put("input_ServiceProviderCode", serviceProviderCode);
        this.callback = callback;
        service.get(authorizationToken, PATH_QUERY, params).enqueue(this);
    }

    public Response reversal(Request request) throws IOException {
        if (securityCredential == null) {
            throw new IllegalArgumentException("Client must contain a securityCredential to revert a transaction");
        }
        if (initiatorIdentifier == null) {
            throw new IllegalArgumentException("Client must contain a initiatorIdentifier to revert a transaction");
        }
        MpesaService service = getService(PORT_REVERSAL);
        MpesaRequest mpesaRequest = MpesaRequest.fromReversalRequest(request, serviceProviderCode,
                securityCredential, initiatorIdentifier);
        retrofit2.Response<MpesaResponse> response = service
                .put(authorizationToken, PATH_REVERSAL, mpesaRequest)
                .execute();
        return parseHttpResponse(response);
    }

    public void reversal(Request request, Callback callback) {
        if (securityCredential == null) {
            throw new IllegalArgumentException("Client must contain a securityCredential to revert a transaction");
        }
        if (initiatorIdentifier == null) {
            throw new IllegalArgumentException("Client must contain a initiatorIdentifier to revert a transaction");
        }
        this.callback = callback;
        MpesaService service = getService(PORT_REVERSAL);
        MpesaRequest mpesaRequest = MpesaRequest.fromReversalRequest(request, serviceProviderCode,
                securityCredential, initiatorIdentifier);
        service.put(authorizationToken, PATH_REVERSAL, mpesaRequest).enqueue(this);
    }

    @Override
    public void onResponse(Call<MpesaResponse> call, retrofit2.Response<MpesaResponse> response) {
        try {
            Response res = parseHttpResponse(response);
            callback.onResponse(res);
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void onFailure(Call<MpesaResponse> call, Throwable t) {
        callback.onError(new Exception(t));
    }

    private MpesaService getService(int port) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host + ":" + port)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MpesaService.class);
    }

    private Response parseHttpResponse(retrofit2.Response<MpesaResponse> response) throws IOException {
        MpesaResponse mpesaResponse;
        if (response.isSuccessful()) {
            mpesaResponse = response.body();
        } else {
            Gson gson = new Gson();
            mpesaResponse = gson.fromJson(response.errorBody().string(), new TypeToken<MpesaResponse>(){}.getType());
        }
        return new Response(mpesaResponse.getConversationId(),
                mpesaResponse.getTransactionId(), mpesaResponse.getResponseDesc(),
                mpesaResponse.getResponseCode(), mpesaResponse.getThirdPartyReference(),
                mpesaResponse.getResponseTransactionStatus());
    }

    private String generateAuthorizationToken(){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA");
            byte[] x509PublicKey = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(x509PublicKey);
            PublicKey pk = keyFactory.generatePublic(publicKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            byte[] encryptedApiKey =  Base64.getEncoder().encode(cipher.doFinal(
                    apiKey.getBytes(StandardCharsets.UTF_8)));
            return "Bearer " + new String(encryptedApiKey, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
