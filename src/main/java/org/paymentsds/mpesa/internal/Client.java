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
        retrofit2.Response<MpesaResponse> response = getHttpCall(request, PORT_C2B).execute();
        return parseHttpResponse(response);
    }

    public void receive(Request request, Callback callback) {
        if (request.getFrom() == null) {
            throw new IllegalArgumentException("Request must contain a 'from' field to receive money.");
        }
        this.callback = callback;
        getHttpCall(request, PORT_C2B).enqueue(this);
    }

    public Response send(Request request) throws IOException {
        if (request.getTo() == null) {
            throw new IllegalArgumentException("Request must contain a 'to' field to send money.");
        }
        retrofit2.Response<MpesaResponse> response;
        // TODO(rosario): Improve this check once we implement regex to validate msisdn
        if (request.getTo().startsWith("258")) {
            response = getHttpCall(request, PORT_B2C).execute();
        } else {
            response = getHttpCall(request, PORT_B2B).execute();
        }
        return parseHttpResponse(response);
    }

    public void send(Request request, Callback callback) {
        if (request.getTo() == null) {
            throw new IllegalArgumentException("Request must contain a 'to' field to send money.");
        }
        this.callback = callback;
        // TODO(rosario): Improve this check once we implement regex to validate msisdn
        if (request.getTo().startsWith("258")) {
            getHttpCall(request, PORT_B2C).enqueue(this);
        } else {
            getHttpCall(request, PORT_B2B).enqueue(this);
        }
    }

    public Response query(Request request) throws IOException {
        retrofit2.Response<MpesaResponse> response = getHttpCall(request, PORT_QUERY).execute();
        return parseHttpResponse(response);
    }

    public void query(Request request, Callback callback) {
        this.callback = callback;
        getHttpCall(request, PORT_QUERY).enqueue(this);
    }

    public Response reversal(Request request) throws IOException {
        if (securityCredential == null) {
            throw new IllegalArgumentException("Client must contain a securityCredential to revert a transaction");
        }
        if (initiatorIdentifier == null) {
            throw new IllegalArgumentException("Client must contain a initiatorIdentifier to revert a transaction");
        }
        retrofit2.Response<MpesaResponse> response = getHttpCall(request, PORT_REVERSAL).execute();
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
        getHttpCall(request, PORT_REVERSAL).enqueue(this);
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

    private Call<MpesaResponse> getHttpCall(Request request, int port) {
        Call<MpesaResponse> responseCall;
        MpesaService service = getService(port);
        switch (port) {
            case PORT_C2B: {
                MpesaRequest mpesaRequest = MpesaRequest.fromC2BRequest(request, serviceProviderCode);
                responseCall = service.post(authorizationToken, PATH_C2B, mpesaRequest);
                break;
            }
            case PORT_B2B: {
                MpesaRequest mpesaRequest = MpesaRequest.fromB2BRequest(request, serviceProviderCode);
                responseCall = service.post(authorizationToken, PATH_B2B, mpesaRequest);
                break;
            }
            case PORT_B2C: {
                MpesaRequest mpesaRequest = MpesaRequest.fromB2CRequest(request, serviceProviderCode);
                responseCall = service.post(authorizationToken, PATH_B2C, mpesaRequest);
                break;
            }
            case PORT_QUERY: {
                HashMap<String, String> params = new HashMap<>();
                params.put("input_QueryReference", request.getReference());
                params.put("input_ThirdPartyReference", request.getTransaction());
                params.put("input_ServiceProviderCode", serviceProviderCode);
                responseCall = service.get(authorizationToken, PATH_QUERY, params);
                break;
            }
            case PORT_REVERSAL: {
                MpesaRequest mpesaRequest = MpesaRequest.fromReversalRequest(request, serviceProviderCode,
                        securityCredential, initiatorIdentifier);
                responseCall = service.put(authorizationToken, PATH_REVERSAL, mpesaRequest);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + port);
        }
        return responseCall;
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
        return Response.fromMpesaResponse(mpesaResponse);
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
