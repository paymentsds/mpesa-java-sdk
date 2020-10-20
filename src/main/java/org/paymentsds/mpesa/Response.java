package org.paymentsds.mpesa;

public class Response {

    private String conversationId;
    private String transactionId;
    private String description;
    private String code;
    private String thirdPartyRef;

    // Query Transaction
    private String transactionStatus;

    Response(
            String conversationId,
            String transactionId,
            String description,
            String code,
            String thirdPartyRef,
            String transactionStatus) {
        this.conversationId = conversationId;
        this.transactionId = transactionId;
        this.description = description;
        this.code = code;
        this.thirdPartyRef = thirdPartyRef;
        this.transactionStatus = transactionStatus;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public String getThirdPartyRef() {
        return thirdPartyRef;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
}
