package org.paymentsds.mpesa;

public interface Callback {
    public void onResponse(Response response);

    public void onError(Exception exception);
}
