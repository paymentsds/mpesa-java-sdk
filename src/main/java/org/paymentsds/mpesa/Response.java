package org.paymentsds.mpesa;

import org.paymentsds.mpesa.internal.MpesaResponse;

public class Response {

    private final String conversationId;
    private final String transactionId;
    private final String description;
    private final String code;
    private final String thirdPartyRef;

    // Query Transaction
    private final String transactionStatus;

    private Response(
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

    public static Response fromMpesaResponse(MpesaResponse mpesaResponse) {
        return new Response(mpesaResponse.getConversationId(),
                mpesaResponse.getTransactionId(), mpesaResponse.getResponseDesc(),
                mpesaResponse.getResponseCode(), mpesaResponse.getThirdPartyReference(),
                mpesaResponse.getResponseTransactionStatus());
    }

    public String getConversationId() { return conversationId; }

    public String getTransactionId() { return transactionId; }

    public String getDescription() { return description; }

    public String getCode() { return code; }

    public String getThirdPartyRef() { return thirdPartyRef; }

    public String getTransactionStatus() { return transactionStatus; }
}
