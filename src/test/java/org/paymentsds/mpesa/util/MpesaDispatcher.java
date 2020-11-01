package org.paymentsds.mpesa.util;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class MpesaDispatcher extends Dispatcher {

    @NotNull
    @Override
    public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
        MockResponse response = new MockResponse().setResponseCode(200);
        if (recordedRequest.getPath().contains("queryTransactionStatus")) {
            response.setBody("{'output_ConversationID': 'dede00b617804d56a344e5b25d102f3f',\n" +
                    " 'output_ResponseCode': 'INS-0',\n" +
                    " 'output_ResponseDesc': 'Request processed successfully',\n" +
                    " 'output_ResponseTransactionStatus': 'Completed',\n" +
                    " 'output_ThirdPartyReference': '11114'}");
        } else {
            switch (recordedRequest.getPath()) {
                case "/ipg/v1x/c2bPayment%2FsingleStage%2F": {
                    response.setBody("{'output_ConversationID': '9854a64364bc430a94fa8e86a3dbd67d',\n" +
                            " 'output_ResponseCode': 'INS-0',\n" +
                            " 'output_ResponseDesc': 'Request processed successfully',\n" +
                            " 'output_ThirdPartyReference': 'E3XYOR',\n" +
                            " 'output_TransactionID': '87x5qbrin91a'}");
                    break;
                }
                case "/ipg/v1x/b2bPayment%2F": {
                    response.setBody("{'output_ConversationID': '8184abea5b434aa0b7b88f7e266e51c5',\n" +
                            " 'output_ResponseCode': 'INS-0',\n" +
                            " 'output_ResponseDesc': 'Request processed successfully',\n" +
                            " 'output_ThirdPartyReference': 'C9TKXC',\n" +
                            " 'output_TransactionID': '8vb72cfueyek'}");
                    break;
                }
                case "/ipg/v1x/b2cPayment%2F": {
                    response.setBody("{'output_ConversationID': '62d3c3493c05437ba2d9c2eb3f9af18d',\n" +
                            " 'output_ResponseCode': 'INS-0',\n" +
                            " 'output_ResponseDesc': 'Request processed successfully',\n" +
                            " 'output_ThirdPartyReference': 'HYAZMG',\n" +
                            " 'output_TransactionID': 'rphk84mjfgoj'}");
                    break;
                }
                case "/ipg/v1x/reversal%2F": {
                    response.setBody("{'output_ConversationID': '1e356eecfdf444db9946f29185d2c435',\n" +
                            " 'output_ResponseCode': 'INS-0',\n" +
                            " 'output_ResponseDesc': 'Request processed successfully',\n" +
                            " 'output_ThirdPartyReference': '3BLM8A',\n" +
                            " 'output_TransactionID': '5kkkxk60ym8j'}");
                    break;
                }
            }
        }
        response.setResponseCode(404);
        return response;
    }
}
