# M-Pesa SDK for JAVA

M-Pesa SDK for JAVA is an unofficial library aiming to help developer businesses integrating every [M-Pesa](https://developer.mpesa.vm.co.mz) operations to their JAVA applications.

## Features <a name="features"></a>

- Receive money from a mobile account to a business account
- Send money from a business account to a mobile account
- Send money from a business account to a another business account
- Revert a transaction
- Query the status of a transaction

## Usage <a name="usage"></a>

### Receive Money from a Mobile Account

```java
import org.paymentsds.mpesa.Callback;
import org.paymentsds.mpesa.Client;
import org.paymentsds.mpesa.Environment;
import org.paymentsds.mpesa.Request;
import org.paymentsds.mpesa.Response;

Client client = new Client.Builder()
    .apiKey("<REPLACE>")
    .publicKey("<REPLACE>")
    .serviceProviderCode("<REPLACE>")
    .initiatorIdentifier("<REPLACE>")
    .environment(Environment.DEVELOPMENT)
    .build();

Request paymentRequest = new Request.Builder()
    .amount(10.0)
    .from("841234567")
    .reference("12345")
    .transaction("12345")
    .build();

// Synchronous Call
try {
    Response response = client.receive(paymentRequest);
    // Handle success scenario
} catch (Exception e) {
    // Handle failure scenario
}

// Asynchronous Call
client.receive(paymentRequest, new Callback() {
    @Override
    public void onResponse(Response response) {
        // Handle success scenario
    }
    
    @Override
    public void onError(Exception e) {
        // Handle failure scenario
    }
});
```

### Send Money to a Mobile Account

```java
import org.paymentsds.mpesa.Callback;
import org.paymentsds.mpesa.Client;
import org.paymentsds.mpesa.Environment;
import org.paymentsds.mpesa.Request;
import org.paymentsds.mpesa.Response;

Client client = new Client.Builder()
    .apiKey("<REPLACE>")
    .publicKey("<REPLACE>")
    .serviceProviderCode("<REPLACE>")
    .initiatorIdentifier("<REPLACE>")
    .environment(Environment.PRODUCTION)
    .build();    

Request paymentRequest = new Request.Builder()
    .amount(10.0)
    .to("841234567")
    .reference("12345")
    .transaction("12345")
    .build();

// Synchronous Call
try {
    Response response = client.send(paymentRequest);
    // Handle success scenario
} catch (Exception e) {
    // Handle failure scenario
}

// Asynchronous Call
client.send(paymentRequest, new Callback() {
    @Override
    public void onResponse(Response response) {
        // Handle success scenario
    }
    
    @Override
    public void onError(Exception e) {
        // Handle failure scenario
    }
});
```

### Send Money to a Business Account

```java
import org.paymentsds.mpesa.Callback;
import org.paymentsds.mpesa.Client;
import org.paymentsds.mpesa.Environment;
import org.paymentsds.mpesa.Request;
import org.paymentsds.mpesa.Response;

Client client = new Client.Builder()
    .apiKey("<REPLACE>")
    .publicKey("<REPLACE>")
    .serviceProviderCode("<REPLACE>")
    .initiatorIdentifier("<REPLACE>")
    .environment(Environment.PRODUCTION)
    .build();    

Request paymentRequest = new Request.Builder()
    .amount(10.0)
    .to("54321")
    .reference("12345")
    .transaction("12345")
    .build();

// Synchronous Call
try {
    Response response = client.send(paymentRequest);
    // Handle success scenario
} catch (Exception e) {
    // Handle failure scenario
}

// Asynchronous Call
client.send(paymentRequest, new Callback() {
    @Override
    public void onResponse(Response response) {
        // Handle success scenario
    }
    
    @Override
    public void onError(Exception e) {
        // Handle failure scenario
    }
});
```

### Revert a Transaction

```java
import org.paymentsds.mpesa.Callback;
import org.paymentsds.mpesa.Client;
import org.paymentsds.mpesa.Environment;
import org.paymentsds.mpesa.Request;
import org.paymentsds.mpesa.Response;

Client client = new Client.Builder()
    .apiKey("<REPLACE>")
    .publicKey("<REPLACE>")
    .serviceProviderCode("<REPLACE>")
    .initiatorIdentifier("<REPLACE>")
    .environment(Environment.PRODUCTION)
    .securityCredential("<REPLACE>")
    .build();    

Request reversalRequest = new Request.Builder()
    .amount(10.0)
    .reference("12345")
    .transaction("12345")
    .build();

// Synchronous Call
try {
    Response response = client.revert(reversalRequest);
    // Handle success scenario
} catch (Exception e) {
    // Handle failure scenario
}

// Asynchronous Call
client.revert(reversalRequest, new Callback() {
    @Override
    public void onResponse(Response response) {
        // Handle success scenario
    }
    
    @Override
    public void onError(Exception e) {
        // Handle failure scenario
    }
});
```

### Query the status of a Transaction

```java
import org.paymentsds.mpesa.Callback;
import org.paymentsds.mpesa.Client;
import org.paymentsds.mpesa.Environment;
import org.paymentsds.mpesa.Request;
import org.paymentsds.mpesa.Response;

Client client = new Client.Builder()
    .apiKey("<REPLACE>")
    .publicKey("<REPLACE>")
    .serviceProviderCode("<REPLACE>")
    .build();    

Request queryRequest = new Request.Builder()
    .reference("12345") // input_ThirdPartyReference
    .subject("12345") // input_QueryReference
    .build();

// Synchronous Call
try {
    Response response = client.query(queryRequest);
    // Handle success scenario
} catch (Exception e) {
    // Handle failure scenario
}

// Asynchronous Call
client.query(queryRequest, new Callback() {
    @Override
    public void onResponse(Response response) {
        // Handle success scenario
    }
    
    @Override
    public void onError(Exception e) {
        // Handle failure scenario
    }
});
```

## Installation <a name="installation"></a>

### Using Composer <a name="#"></a>

### Manual Installation <a name="#"></a>

### Friends <a name="related-projects/friends"></a>

- [M-Pesa SDK for Javascript](https://github.com/paymentsds/mpesa-js-sdk)
- [M-Pesa SDK for Ruby](https://github.com/paymentsds/mpesa-ruby-sdk)
- [M-Pesa SDK for Python](https://github.com/paymentsds/mpesa-python-sdk)


## Contributing <a name="contributing"></a>

## Changelog <a name="changelog"></a>

## Authors <a name="authors"></a>

- [Anísio Mandlate](https://github.com/AnisioMandlate)
- [Edson Michaque](https://github.com/edsonmichaque)
- [Elton Laice](https://github.com/eltonlaice)
- [Nélio Macombo](https://github.com/neliomacombo)

## Credits <a name="credits"></a>

- [All Contributors](../../contributors)

## License <a name="license"></a>

Copyright 2020 Anísio Mandlate, Edson Michaque, Elton Laice and Nélio Macombo

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
