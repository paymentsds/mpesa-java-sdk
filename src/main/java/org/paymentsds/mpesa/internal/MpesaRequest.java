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
}
