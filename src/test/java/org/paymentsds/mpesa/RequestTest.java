package org.paymentsds.mpesa;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void canBuildC2bRequest() {
        double expectedAmount = 20.0;
        String expectedFrom = "258844471329";
        String expectedReference = "12345";
        String expectedTransaction = "t1234";

        Request request = new Request.Builder()
                .from(expectedFrom)
                .amount(expectedAmount)
                .reference(expectedReference)
                .transaction(expectedTransaction)
                .build();

        assertEquals(request.getAmount(), expectedAmount, 0.0);
        assertEquals(request.getFrom(), expectedFrom);
        assertEquals(request.getReference(), expectedReference);
        assertEquals(request.getTransaction(), expectedTransaction);
    }

    @Test
    public void canBuildB2cRequest() {
        double expectedAmount = 20.0;
        String expectedReference = "12345";
        String expectedTransaction = "t1234";
        String expectedTo = "258844471329";

        Request request = new Request.Builder()
                .to(expectedTo)
                .amount(expectedAmount)
                .reference(expectedReference)
                .transaction(expectedTransaction)
                .build();

        assertEquals(request.getAmount(), expectedAmount, 0.0);
        assertEquals(request.getTo(), expectedTo);
        assertEquals(request.getReference(), expectedReference);
        assertEquals(request.getTransaction(), expectedTransaction);
    }

    @Test
    public void canBuildB2bRequest() {
        double expectedAmount = 20.0;
        String expectedFrom = "258844471329";
        String expectedReference = "12345";
        String expectedTransaction = "t1234";
        String expectedTo = "979797";
        String expectedSubject = "s1234";

        Request request = new Request.Builder()
                .to(expectedTo)
                .amount(expectedAmount)
                .reference(expectedReference)
                .transaction(expectedTransaction)
                .build();

        assertEquals(request.getAmount(), expectedAmount, 0.0);
        assertEquals(request.getTo(), expectedTo);
        assertEquals(request.getReference(), expectedReference);
        assertEquals(request.getTransaction(), expectedTransaction);
    }

    @Test
    public void canBuildQueryRequest() {
        String expectedReference = "12345";
        String expectedSubject = "s1234";

        Request request = new Request.Builder()
                .reference(expectedReference)
                .subject(expectedSubject)
                .build();

        assertEquals(request.getReference(), expectedReference);
        assertEquals(request.getSubject(), expectedSubject);
    }

    @Test
    public void canBuildPartialReversalRequest() {
        double expectedAmount = 20.0;
        String expectedReference = "12345";
        String expectedTransaction = "t1234";

        Request request = new Request.Builder()
                .amount(expectedAmount)
                .reference(expectedReference)
                .transaction(expectedTransaction)
                .build();

        assertEquals(request.getAmount(), expectedAmount, 0.0);
        assertEquals(request.getReference(), expectedReference);
        assertEquals(request.getTransaction(), expectedTransaction);
    }

    @Test
    public void canBuildFullReversalRequest() {
        String expectedReference = "12345";
        String expectedTransaction = "t1234";

        Request request = new Request.Builder()
                .reference(expectedReference)
                .transaction(expectedTransaction)
                .build();

        assertEquals(request.getReference(), expectedReference);
        assertEquals(request.getTransaction(), expectedTransaction);
    }

    @Test
    public void cantBuildRequestWithNoReference() {
        try {
            new Request.Builder()
                    .from("258841234567")
                    .amount(10.0)
                    .transaction("T1234")
                    .build();
            fail();
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                assertTrue(e.getMessage().contains("reference"));
            } else {
                fail();
            }
        }
    }

    @Test
    public void cantBuildRequestWithFromAndTo() {
        try {
            new Request.Builder()
                    .reference("12345")
                    .from("258841234567")
                    .to("258851234567")
                    .build();
            fail();
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
}