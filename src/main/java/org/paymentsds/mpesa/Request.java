package org.paymentsds.mpesa;

public class Request {

    // Receive Money
    private final double amount;
    private final String from;
    private final String reference;
    private final String transaction;

    // Send Money
    private final String to;

    // Query status
    private final String subject;

    private Request(
            double amount,
            String from,
            String reference,
            String transaction,
            String to,
            String subject) {
        this.amount = amount;
        this.from = from;
        this.reference = reference;
        this.transaction = transaction;
        this.to = to;
        this.subject = subject;
    }

    public double getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getReference() {
        return reference;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public static class Builder {
        // Receive Money
        double amount = 0.0;
        String from;
        String reference;
        String transaction;

        // Send Money
        String to;

        // Query status
        String subject;

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder transaction(String transaction) {
            this.transaction = transaction;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Request build() {
            if (from != null && to != null) {
                throw new IllegalArgumentException("Request cannot contain both 'from' and 'to' arguments");
            }

            // This is required by all requests
            if (reference == null) {
                throw new IllegalArgumentException("Request must contain a reference");
            }

            // Handle C2B, B2C and B2B
            if (from != null || to != null) {
                if (amount == 0.0) {
                    throw new IllegalArgumentException("Request must contain an amount greater than 0.0");
                }
                if (transaction == null) {
                    throw new IllegalArgumentException("Request must contain a transaction reference");
                }
            }

            // Handle reversals
            if (to == null && from == null && subject == null) {
                if (amount == 0.0) {
                    throw new IllegalArgumentException("Request must contain an amount greater than 0.0");
                }
                if (transaction == null) {
                    throw new IllegalArgumentException("Request must contain a transaction reference");
                }
            }
            return new Request(amount, from, reference, transaction, to, subject);
        }
    }
}
