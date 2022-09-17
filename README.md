# JAVA M-Pesa SDK


<p align="center"><a href="https://jitpack.io/#paymentsds/mpesa-java-sdk"><img src="https://img.shields.io/github/downloads/paymentsds/mpesa-java-sdk/total" alt="Total Downloads"></a> <a href="https://jitpack.io/#paymentsds/mpesa-java-sdk"><img src="https://img.shields.io/jitpack/version/com.github.paymentsds/mpesa-java-sdk" alt="Latest Stable Version"></a> <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache_2.0-blue.svg" alt="License"></a></p>

This is a library willing to help you to integrate the [Vodacom M-Pesa](https://developer.mpesa.vm.co.mz) operations to your application.

<br>

### Features

Using this library, you can implement the following operations:

- Receive money from a mobile account to a business account (C2B)
- Send money from a business account to a mobile account (B2C)
- Send money from a business account to another business account (B2B)
- Revert any of the transactions above mentioned
- Query the status of a transaction

<br><br>

## Requirements

- Valid credentials obtained from the [Mpesa Developer](https://developer.mpesa.vm.co.mz) portal
- Port 18352 open on your server (usually open on local env)


<br><br>


## Installation

<br>

### Using Gradle

1. Add jitpack to your root `build.gradle` file, under `repositories`:
    ```groovy
    repositories {
        // ... other repositories here ...
        maven { url 'https://jitpack.io' }
    }
    ```

2. Add the dependency:
    ```groovy
    dependencies {
        implementation 'com.github.paymentsds:mpesa-java-sdk:0.1.0-alpha1'
    }
    ```
<br>

### Using Maven

1. Add jitpack to your `pom.xml` file, under `repositories`:
    ```xml
    <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
    </repositories>
    ```

2. Add the dependency:
    ```xml
    <dependency>
       <groupId>com.github.paymentsds</groupId>
       <artifactId>mpesa-java-sdk</artifactId>
       <version>0.1.0-alpha1</version>
    </dependency>
    ```

<br>

### Manual Installation
```bash
git clone https://github.com/paymentsds/mpesa-sdk
```

<br><br>


## Usage

Using this SDK is very simple and fast, let us see some examples:

<br>

#### C2B Transaction (Receive money from mobile account)

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

<br>

#### B2C Transaction (Sending money to mobile account)

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

<br>

#### B2B Transaction (Sending money to business account)

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

<br>


#### Transaction Reversal

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

<br>

#### Query the transaction status

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

<br><br>

## Friends

- [M-Pesa SDK for Javascript](https://github.com/paymentsds/mpesa-js-sdk)
- [M-Pesa SDK for Python](https://github.com/paymentsds/mpesa-python-sdk)
- [M-Pesa SDK for PHP](https://github.com/paymentsds/mpesa-php-sdk)
- [M-Pesa SDK for Ruby](https://github.com/paymentsds/mpesa-ruby-sdk)


<br><br>

## Authors <a name="authors"></a>

- [Name](https://github.com/username)


<br><br>

## Contributing

Thank you for considering contributing to this package. If you wish to do it, email us at [developers@paymentsds.org](mailto:developers@paymentsds.org) and we will get back to you as soon as possible.


<br><br>

## Security Vulnerabilities

If you discover a security vulnerability, please email us at [developers@paymentsds.org](mailto:developers@paymentsds.org) and we will address the issue with the needed urgency.

<br><br>

## License

Copyright 2022 &copy; The PaymentsDS Team

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
