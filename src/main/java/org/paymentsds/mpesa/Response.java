package org.paymentsds.mpesa;

public class Response {

    private String conversationId;
    private String transactionId;
    private String responseDesc;
    private String responseCode;
    private String thirdPartyRef;

    // Query Transaction
    private String queryRef;
    private String serviceProviderCode;

    // C2B, B2B, B2C and Reversal
    Response(
            String conversationId,
            String transactionId,
            String responseDesc,
            String responseCode,
            String thirdPartyRef) {
        this.conversationId = conversationId;
        this.transactionId = transactionId;
        this.responseDesc = responseDesc;
        this.responseCode = responseCode;
        this.thirdPartyRef = thirdPartyRef;
    }

    // Query Transaction
    Response(String thirdPartyRef, String queryRef, String serviceProviderCode) {
        this.thirdPartyRef = thirdPartyRef;
        this.queryRef = queryRef;
        this.serviceProviderCode = serviceProviderCode;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getThirdPartyRef() {
        return thirdPartyRef;
    }

    public String getQueryRef() {
        return queryRef;
    }

    public String getServiceProviderCode() {
        return serviceProviderCode;
    }
}
