package org.paymentsds.mpesa;

import java.io.IOException;

public class Client {

    private final org.paymentsds.mpesa.internal.Client client;

    private Client(
            String apiKey,
            String publicKey,
            String serviceProviderCode,
            String initiatorIdentifier,
            String host,
            String securityCredential) {
        client = new org.paymentsds.mpesa.internal.Client(apiKey, publicKey, serviceProviderCode,
                initiatorIdentifier, host, securityCredential);
    }

    public Response receive(Request request) throws IOException {
        return client.receive(request);
    }

    public void receive(Request request, Callback callback) {
        client.receive(request, callback);
    }

    public Response send(Request request) throws IOException {
        return client.send(request);
    }

    public void send(Request request, Callback callback) {
        client.send(request, callback);
    }

    public Response query(Request request) throws IOException {
        return client.query(request);
    }

    public void query(Request request, Callback callback) {
        client.query(request, callback);
    }

    public Response reversal(Request request) throws IOException {
        return client.reversal(request);
    }

    public void reversal(Request request, Callback callback) {
        client.reversal(request, callback);
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
