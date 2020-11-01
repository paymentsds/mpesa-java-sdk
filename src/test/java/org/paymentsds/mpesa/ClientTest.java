package org.paymentsds.mpesa;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.paymentsds.mpesa.util.MpesaDispatcher;

import java.io.IOException;

import static org.junit.Assert.*;

public class ClientTest {
    private static MockWebServer server;
    private static Client client;

    @BeforeClass
    public static void setupMockWebServer() throws IOException {
        server = new MockWebServer();
        server.setDispatcher(new MpesaDispatcher());
        server.start();

        client = new Client.Builder()
                .apiKey("kjxpog1cdqqshey5pq5wbu5vh4lj95qb")
                .publicKey("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmptSWqV7cGUUJJhUBxsMLonux24u+FoTlrb+4Kgc6092JIszmI1QUoMohaDDXSVueXx6IXwYGsjjWY32HGXj1iQhkALXfObJ4DqXn5h6E8y5/xQYNAyd5bpN5Z8r892B6toGzZQVB7qtebH4apDjmvTi5FGZVjVYxalyyQkj4uQbbRQjgCkubSi45Xl4CGtLqZztsKssWz3mcKncgTnq3DHGYYEYiKq0xIj100LGbnvNz20Sgqmw/cH+Bua4GJsWYLEqf/h/yiMgiBbxFxsnwZl0im5vXDlwKPw+QnO2fscDhxZFAwV06bgG0oEoWm9FnjMsfvwm0rUNYFlZ+TOtCEhmhtFp+Tsx9jPCuOd5h2emGdSKD8A6jtwhNa7oQ8RtLEEqwAn44orENa1ibOkxMiiiFpmmJkwgZPOG/zMCjXIrrhDWTDUOZaPx/lEQoInJoE2i43VN/HTGCCw8dKQAwg0jsEXau5ixD0GUothqvuX3B9taoeoFAIvUPEq35YulprMM7ThdKodSHvhnwKG82dCsodRwY428kg2xM/UjiTENog4B6zzZfPhMxFlOSFX4MnrqkAS+8Jamhy1GgoHkEMrsT5+/ofjCx0HjKbT5NuA2V/lmzgJLl3jIERadLzuTYnKGWxVJcGLkWXlEPYLbiaKzbJb2sYxt+Kt5OxQqC1MCAwEAAQ==")
                .serviceProviderCode("171717")
                .environment(Environment.DEVELOPMENT)
                .host(server.url(""))
                .securityCredential("Mpesa2019")
                .initiatorIdentifier("Mpesa2018")
                .build();
    }

    @AfterClass
    public static void shutdownMockWebServer() throws IOException {
        server.shutdown();
    }

    @Test
    public void testSyncReceive() {
        Request request = new Request.Builder()
                .amount(10.0)
                .reference("12345")
                .transaction("12345")
                .from("258841234567")
                .build();
        try {
            Response res = client.receive(request);
            assertEquals(res.getCode(), "INS-0");
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSyncSendToBusiness() {
        Request request = new Request.Builder()
                .amount(10.0)
                .reference("12345")
                .transaction("12346")
                .to("979797")
                .build();
        try {
            Response res = client.send(request);
            assertEquals(res.getCode(), "INS-0");
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSyncSendToCustomer() {
        Request request = new Request.Builder()
                .amount(10.0)
                .reference("12345")
                .transaction("12346")
                .to("258844471329")
                .build();
        try {
            Response res = client.send(request);
            assertEquals(res.getCode(), "INS-0");
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSyncPartialReversal() {
        Request request = new Request.Builder()
                .amount(10.0)
                .reference("12345")
                .transaction("T12345")
                .build();
        try {
            Response res = client.reversal(request);
            assertEquals(res.getCode(), "INS-0");
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSyncFullReversal() {
        Request request = new Request.Builder()
                .reference("12345")
                .transaction("T12345")
                .build();
        try {
            Response res = client.reversal(request);
            assertEquals(res.getCode(), "INS-0");
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSyncQuery() {
        Request request = new Request.Builder()
                .reference("12345")
                .subject("T12345")
                .build();
        try {
            Response res = client.query(request);
            assertEquals(res.getCode(), "INS-0");
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}