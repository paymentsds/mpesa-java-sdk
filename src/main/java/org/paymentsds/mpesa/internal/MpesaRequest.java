package org.paymentsds.mpesa.internal;

import org.paymentsds.mpesa.Request;

public class MpesaRequest {

    private String input_TransactionReference;
    private String input_Amount;
    private String input_ThirdPartyReference;
    private String input_CustomerMSISDN;
    private String input_ServiceProviderCode;

    // B2B
    private String input_PrimaryPartyCode;
    private String input_ReceiverPartyCode;

    // Reversal
    private String input_ReversalAmount;
    private String input_InitiatorIdentifier;
    private String input_SecurityCredential;
    private String input_TransactionID;

    private MpesaRequest() { }

    public static MpesaRequest fromC2BRequest(Request request, String serviceProviderCode) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setInput_Amount(request.getAmount() + "");
        mpesaRequest.setInput_TransactionReference(request.getTransaction());
        mpesaRequest.setInput_ThirdPartyReference(request.getReference());
        mpesaRequest.setInput_CustomerMSISDN(request.getFrom());
        mpesaRequest.setInput_ServiceProviderCode(serviceProviderCode);
        return mpesaRequest;
    }

    public static MpesaRequest fromB2CRequest(Request request, String serviceProviderCode) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setInput_Amount(request.getAmount() + "");
        mpesaRequest.setInput_TransactionReference(request.getTransaction());
        mpesaRequest.setInput_ThirdPartyReference(request.getReference());
        mpesaRequest.setInput_CustomerMSISDN(request.getTo());
        mpesaRequest.setInput_ServiceProviderCode(serviceProviderCode);
        return mpesaRequest;
    }

    public static MpesaRequest fromB2BRequest(Request request, String primaryPartyCode) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setInput_Amount(request.getAmount() + "");
        mpesaRequest.setInput_TransactionReference(request.getTransaction());
        mpesaRequest.setInput_ThirdPartyReference(request.getReference());
        mpesaRequest.setInput_PrimaryPartyCode(primaryPartyCode);
        mpesaRequest.setInput_ReceiverPartyCode(request.getTo());
        return mpesaRequest;
    }

    public static MpesaRequest fromReversalRequest(
            Request request,
            String serviceProviderCode,
            String securityCredential,
            String initiatorIdentifier) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setInput_TransactionID(request.getTransaction());
        mpesaRequest.setInput_SecurityCredential(securityCredential);
        mpesaRequest.setInput_InitiatorIdentifier(initiatorIdentifier);
        mpesaRequest.setInput_ThirdPartyReference(request.getReference());
        mpesaRequest.setInput_ServiceProviderCode(serviceProviderCode);
        mpesaRequest.setInput_ReversalAmount(request.getAmount() + "");
        return mpesaRequest;
    }

    public String getInput_TransactionReference() {
        return input_TransactionReference;
    }

    public void setInput_TransactionReference(String input_TransactionReference) {
        this.input_TransactionReference = input_TransactionReference;
    }

    public String getInput_CustomerMSISDN() {
        return input_CustomerMSISDN;
    }

    public void setInput_CustomerMSISDN(String input_CustomerMSISDN) {
        this.input_CustomerMSISDN = input_CustomerMSISDN;
    }

    public String getInput_Amount() {
        return input_Amount;
    }

    public void setInput_Amount(String input_Amount) {
        this.input_Amount = input_Amount;
    }

    public String getInput_ThirdPartyReference() {
        return input_ThirdPartyReference;
    }

    public void setInput_ThirdPartyReference(String input_ThirdPartyReference) {
        this.input_ThirdPartyReference = input_ThirdPartyReference;
    }

    public String getInput_ServiceProviderCode() {
        return input_ServiceProviderCode;
    }

    public void setInput_ServiceProviderCode(String input_ServiceProviderCode) {
        this.input_ServiceProviderCode = input_ServiceProviderCode;
    }

    public String getInput_PrimaryPartyCode() {
        return input_PrimaryPartyCode;
    }

    public void setInput_PrimaryPartyCode(String input_PrimaryPartyCode) {
        this.input_PrimaryPartyCode = input_PrimaryPartyCode;
    }

    public String getInput_ReceiverPartyCode() {
        return input_ReceiverPartyCode;
    }

    public void setInput_ReceiverPartyCode(String input_ReceiverPartyCode) {
        this.input_ReceiverPartyCode = input_ReceiverPartyCode;
    }

    public String getInput_ReversalAmount() {
        return input_ReversalAmount;
    }

    public void setInput_ReversalAmount(String input_ReversalAmount) {
        this.input_ReversalAmount = input_ReversalAmount;
    }

    public String getInput_InitiatorIdentifier() {
        return input_InitiatorIdentifier;
    }

    public void setInput_InitiatorIdentifier(String input_InitiatorIdentifier) {
        this.input_InitiatorIdentifier = input_InitiatorIdentifier;
    }

    public String getInput_SecurityCredential() {
        return input_SecurityCredential;
    }

    public void setInput_SecurityCredential(String input_SecurityCredential) {
        this.input_SecurityCredential = input_SecurityCredential;
    }

    public String getInput_TransactionID() {
        return input_TransactionID;
    }

    public void setInput_TransactionID(String input_TransactionID) {
        this.input_TransactionID = input_TransactionID;
    }
}
