package org.paymentsds.mpesa.internal;

import com.google.gson.annotations.SerializedName;
import org.paymentsds.mpesa.Request;

public class MpesaRequest {

    @SerializedName("input_TransactionReference")
    private String transactionReference;
    @SerializedName("input_Amount")
    private String amount;
    @SerializedName("input_ThirdPartyReference")
    private String thirdPartyReference;
    @SerializedName("input_CustomerMSISDN")
    private String customerMsisdn;
    @SerializedName("input_ServiceProviderCode")
    private String serviceProviderCode;

    // B2B
    @SerializedName("input_PrimaryPartyCode")
    private String primaryPartyCode;
    @SerializedName("input_ReceiverPartyCode")
    private String receiverPartyCode;

    // Reversal
    @SerializedName("input_ReversalAmount")
    private String reversalAmount;
    @SerializedName("input_InitiatorIdentifier")
    private String initiatorIdentifier;
    @SerializedName("input_SecurityCredential")
    private String securityCredential;
    @SerializedName("input_TransactionID")
    private String transactionID;

    private MpesaRequest() { }

    public static MpesaRequest fromC2BRequest(Request request, String serviceProviderCode) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setAmount(request.getAmount() + "");
        mpesaRequest.setTransactionReference(request.getTransaction());
        mpesaRequest.setThirdPartyReference(request.getReference());
        mpesaRequest.setCustomerMsisdn(request.getFrom());
        mpesaRequest.setServiceProviderCode(serviceProviderCode);
        return mpesaRequest;
    }

    public static MpesaRequest fromB2CRequest(Request request, String serviceProviderCode) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setAmount(request.getAmount() + "");
        mpesaRequest.setTransactionReference(request.getTransaction());
        mpesaRequest.setThirdPartyReference(request.getReference());
        mpesaRequest.setCustomerMsisdn(request.getTo());
        mpesaRequest.setServiceProviderCode(serviceProviderCode);
        return mpesaRequest;
    }

    public static MpesaRequest fromB2BRequest(Request request, String primaryPartyCode) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setAmount(request.getAmount() + "");
        mpesaRequest.setTransactionReference(request.getTransaction());
        mpesaRequest.setThirdPartyReference(request.getReference());
        mpesaRequest.setPrimaryPartyCode(primaryPartyCode);
        mpesaRequest.setReceiverPartyCode(request.getTo());
        return mpesaRequest;
    }

    public static MpesaRequest fromReversalRequest(
            Request request,
            String serviceProviderCode,
            String securityCredential,
            String initiatorIdentifier) {
        MpesaRequest mpesaRequest = new MpesaRequest();
        mpesaRequest.setTransactionID(request.getTransaction());
        mpesaRequest.setSecurityCredential(securityCredential);
        mpesaRequest.setInitiatorIdentifier(initiatorIdentifier);
        mpesaRequest.setThirdPartyReference(request.getReference());
        mpesaRequest.setServiceProviderCode(serviceProviderCode);
        mpesaRequest.setReversalAmount(request.getAmount() + "");
        return mpesaRequest;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getCustomerMsisdn() {
        return customerMsisdn;
    }

    public void setCustomerMsisdn(String customerMsisdn) {
        this.customerMsisdn = customerMsisdn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getThirdPartyReference() {
        return thirdPartyReference;
    }

    public void setThirdPartyReference(String thirdPartyReference) {
        this.thirdPartyReference = thirdPartyReference;
    }

    public String getServiceProviderCode() {
        return serviceProviderCode;
    }

    public void setServiceProviderCode(String serviceProviderCode) {
        this.serviceProviderCode = serviceProviderCode;
    }

    public String getPrimaryPartyCode() {
        return primaryPartyCode;
    }

    public void setPrimaryPartyCode(String primaryPartyCode) {
        this.primaryPartyCode = primaryPartyCode;
    }

    public String getReceiverPartyCode() {
        return receiverPartyCode;
    }

    public void setReceiverPartyCode(String receiverPartyCode) {
        this.receiverPartyCode = receiverPartyCode;
    }

    public String getReversalAmount() {
        return reversalAmount;
    }

    public void setReversalAmount(String reversalAmount) {
        this.reversalAmount = reversalAmount;
    }

    public String getInitiatorIdentifier() {
        return initiatorIdentifier;
    }

    public void setInitiatorIdentifier(String initiatorIdentifier) {
        this.initiatorIdentifier = initiatorIdentifier;
    }

    public String getSecurityCredential() {
        return securityCredential;
    }

    public void setSecurityCredential(String securityCredential) {
        this.securityCredential = securityCredential;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
}
