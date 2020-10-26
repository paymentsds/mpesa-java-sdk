package org.paymentsds.mpesa;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.paymentsds.mpesa.internal.MpesaRequest;
import org.paymentsds.mpesa.internal.MpesaResponse;
import org.paymentsds.mpesa.internal.MpesaService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Client {
    private final String apiKey;
    private final String publicKey;
    private final String serviceProviderCode;
    private final String initiatorIdentifier;
    private final String host;
    private final String securityCredential;

    private Client(
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
    }

    public Response receive(Request request) throws IOException {
        if (request.getFrom() == null) {
            throw new IllegalArgumentException("Request must contain a 'from' field to receive money.");
        }
        MpesaService service = getService(18352);
        MpesaRequest mpesaRequest = MpesaRequest.fromC2BRequest(request, serviceProviderCode);
        retrofit2.Response<MpesaResponse> response = service.c2b(generateAuthorizationToken(), mpesaRequest).execute();
        return parseHttpResponse(response);
    }

    public void receive(Request request, Callback callback) {
        if (request.getFrom() == null) {
            throw new IllegalArgumentException("Request must contain a 'from' field to receive money.");
        }
        MpesaService service = getService(18352);
        MpesaRequest mpesaRequest = MpesaRequest.fromC2BRequest(request, serviceProviderCode);
        service.c2b(generateAuthorizationToken(), mpesaRequest).enqueue(new retrofit2.Callback<MpesaResponse>() {
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
        });
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
            service = getService(18345);
            mpesaRequest = MpesaRequest.fromB2CRequest(request, serviceProviderCode);
            response = service.b2c(generateAuthorizationToken(), mpesaRequest).execute();
        } else {
            service = getService(18349);
            mpesaRequest = MpesaRequest.fromB2BRequest(request, serviceProviderCode);
            response = service.b2b(generateAuthorizationToken(), mpesaRequest).execute();
        }
        return parseHttpResponse(response);
    }

    public void send(Request request, Callback callback) {
        if (request.getTo() == null) {
            throw new IllegalArgumentException("Request must contain a 'to' field to send money.");
        }
        retrofit2.Callback<MpesaResponse> retrofitCallback = new retrofit2.Callback<MpesaResponse>() {
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
        };
        MpesaRequest mpesaRequest;
        MpesaService service;
        // TODO(rosario): Improve this check once we implement regex to validate msisdn
        if (request.getTo().startsWith("258")) {
            mpesaRequest = MpesaRequest.fromB2CRequest(request, serviceProviderCode);
            service = getService(18345);
            service.b2c(generateAuthorizationToken(), mpesaRequest).enqueue(retrofitCallback);
        } else {
            mpesaRequest = MpesaRequest.fromB2BRequest(request, serviceProviderCode);
            service = getService(18349);
            service.b2b(generateAuthorizationToken(), mpesaRequest).enqueue(retrofitCallback);
        }
    }

    public Response query(Request request) throws IOException {
        MpesaService service = getService(18353);
        retrofit2.Response<MpesaResponse> response = service
                .query(generateAuthorizationToken(), request.getSubject(), request.getReference(), serviceProviderCode)
                .execute();
        return parseHttpResponse(response);
    }

    public void query(Request request, Callback callback) {
        MpesaService service = getService(18353);
        service.query(generateAuthorizationToken(), request.getSubject(), request.getReference(), serviceProviderCode)
                .enqueue(new retrofit2.Callback<MpesaResponse>() {
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
                });
    }

    public Response reversal(Request request) throws IOException {
        if (securityCredential == null) {
            throw new IllegalArgumentException("Client must contain a securityCredential to revert a transaction");
        }
        if (initiatorIdentifier == null) {
            throw new IllegalArgumentException("Client must contain a initiatorIdentifier to revert a transaction");
        }
        MpesaService service = getService(18354);
        MpesaRequest mpesaRequest = MpesaRequest.fromReversalRequest(request, serviceProviderCode,
                securityCredential, initiatorIdentifier);
        retrofit2.Response<MpesaResponse> response = service
                .reversal(generateAuthorizationToken(), mpesaRequest)
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
        MpesaService service = getService(18354);
        MpesaRequest mpesaRequest = MpesaRequest.fromReversalRequest(request, serviceProviderCode,
                securityCredential, initiatorIdentifier);
        service.reversal(generateAuthorizationToken(), mpesaRequest)
                .enqueue(new retrofit2.Callback<MpesaResponse>() {
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
                });
    }

    private Response parseHttpResponse(retrofit2.Response<MpesaResponse> response) throws IOException {
        MpesaResponse mpesaResponse;
        if (response.isSuccessful()) {
            mpesaResponse = response.body();
        } else {
            Gson gson = new Gson();
            mpesaResponse = gson.fromJson(response.errorBody().string(), new TypeToken<MpesaResponse>(){}.getType());
        }
        return new Response(mpesaResponse.getOutput_ConversationID(),
                mpesaResponse.getOutput_TransactionID(), mpesaResponse.getOutput_ResponseDesc(),
                mpesaResponse.getOutput_ResponseCode(), mpesaResponse.getOutput_ThirdPartyReference(),
                mpesaResponse.getOutput_ResponseTransactionStatus());
    }

    private MpesaService getService(int port) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.host + ":" + port)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MpesaService.class);
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
                    apiKey.getBytes("UTF-8")));
            return "Bearer " + new String(encryptedApiKey, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class Builder {
        String apiKey;
        String publicKey;
        String serviceProviderCode;
        String initiatorIdentifier;
        String host;
        String securityCredential;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder publicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public Builder serviceProviderCode(String serviceProviderCode) {
            this.serviceProviderCode = serviceProviderCode;
            return this;
        }

        public Builder initiatorIdentifier(String initiatorIdentifier) {
            this.initiatorIdentifier = initiatorIdentifier;
            return this;
        }

        public Builder environment(Environment environment) {
            if (environment == Environment.DEVELOPMENT) {
                host = "https://api.sandbox.vm.co.mz";
            } else {
                host = "https://api.vm.co.mz";
            }
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder securityCredential(String securityCredential) {
            this.securityCredential = securityCredential;
            return this;
        }

        public Client build() {
            if (apiKey == null) {
                throw new IllegalArgumentException("Client must contain an apiKey");
            }
            if (publicKey == null) {
                throw new IllegalArgumentException("Client must contain a publicKey");
            }
            if (host == null) {
                throw new IllegalArgumentException("Client must contain either a host or an environment");
            }
            if (serviceProviderCode == null) {
                throw new IllegalArgumentException("Client must contain serviceProviderCode");
            }
            return new Client(apiKey, publicKey, serviceProviderCode, initiatorIdentifier, host, securityCredential);
        }
    }
}
