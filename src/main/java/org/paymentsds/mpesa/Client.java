package org.paymentsds.mpesa;

public class Client {
    private final String apiKey;
    private final String publicKey;
    private final String serviceProviderCode;
    private final String initiatorIdentifier;
    private final Environment environment;
    private final String host;
    private final String securityCredential;

    private Client(
            String apiKey,
            String publicKey,
            String serviceProviderCode,
            String initiatorIdentifier,
            Environment environment,
            String host,
            String securityCredential) {
        this.apiKey = apiKey;
        this.publicKey = publicKey;
        this.serviceProviderCode = serviceProviderCode;
        this.initiatorIdentifier = initiatorIdentifier;
        this.environment = environment;
        this.host = host;
        this.securityCredential = securityCredential;
    }

    /*public Response receive(Request request) {
        TODO
    }*/

    public void receive(Request request, Callback callback) {
        // TODO

    }

    public static class Builder {
        String apiKey;
        String publicKey;
        String serviceProviderCode;
        String initiatorIdentifier;
        Environment environment;
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
            this.environment = environment;
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
                throw new IllegalArgumentException("Client must contain a host");
            }
            if (environment == null) {
                environment = Environment.DEVELOPMENT;
            }
            return new Client(apiKey, publicKey, serviceProviderCode, initiatorIdentifier,
                    environment, host, securityCredential);
        }
    }
}
