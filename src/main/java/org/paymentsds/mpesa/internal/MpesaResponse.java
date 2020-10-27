package org.paymentsds.mpesa.internal;

import com.google.gson.annotations.SerializedName;

public class MpesaResponse {
    @SerializedName("output_ConversationID")
    private String conversationId;
    @SerializedName("output_TransactionID")
    private String transactionId;
    @SerializedName("output_ResponseDesc")
    private String responseDesc;
    @SerializedName("output_ResponseCode")
    private String responseCode;
    @SerializedName("output_ThirdPartyReference")
    private String thirdPartyReference;

    // Query Transaction Status
    @SerializedName("output_ResponseTransactionStatus")
    private String responseTransactionStatus;

    public MpesaResponse() { }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getThirdPartyReference() {
        return thirdPartyReference;
    }

    public void setThirdPartyReference(String thirdPartyReference) {
        this.thirdPartyReference = thirdPartyReference;
    }

    public String getResponseTransactionStatus() {
        return responseTransactionStatus;
    }

    public void setResponseTransactionStatus(String responseTransactionStatus) {
        this.responseTransactionStatus = responseTransactionStatus;
    }
}
