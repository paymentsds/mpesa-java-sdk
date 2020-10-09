# M-Pesa SDK for JAVA

M-Pesa SDK for JAVA is an unofficial library aiming to help develbusinesses integrating every [M-Pesa](https://developer.mpesa.vm.co.mz) operations to their JAVA applications.

## Features <a name="features"></a>

- Receive money from a mobile account to a business account
- Send money from a business account to a mobile account
- Send money from a business account to a another business account
- Revert a transaction
- Query the status of a transaction

## Usage <a name="usage"></a>

### Receive Money from a Mobile Account

```java
Client client = new ClientBuilder()
    .setApiKey("<REPLACE>")
    .setPublicKey("<REPLACE>")
    .setServiceProviderCode("<REPLACE>")
    .build();

ReceiveRequest paymentData = new ReceiveRequestBuilder()
    .setFrom('841234567')
    .setReference('11114')
    .setTransactionRef('T12344CC')
    .setAmount(10.0)
    .build();
    
client.receive(paymentData).enqueue(new Callback<ReceiveResponse> {
    @Override
    public void onResponse(Call<ReceiveResponse> call, Response<ReceiveResponse> response) {
        // handle success
    }
    
    @Override
    public void onFailure(Call<ReceiveResponse> call, Throwable t) {
        // handle errors
    }
});
```

### Send Money to a Mobile Account

```java
Client client = new ClientBuilder()
    .setApiKey("<REPLACE>")
    .setPublicKey("<REPLACE>")
    .setServiceProviderCode("<REPLACE>")
    .build();

SendRequest paymentData = new SendRequestBuilder()
    .setTo('841234567')
    .setReference('11114')
    .setTransactionRef('T12344CC')
    .setAmount(10.0)
    .build();
    
client.send(paymentData).enqueue(new Callback<SendResponse> {
    @Override
    public void onResponse(Call<SendResponse> call, Response<SendResponse> response) {
        // handle success
    }
    
    @Override
    public void onFailure(Call<SendResponse> call, Throwable t) {
        // handle errors
    }
});
```

### Send Money to a Business Account

```java
Client client = new ClientBuilder()
    .setApiKey("<REPLACE>")
    .setPublicKey("<REPLACE>")
    .setServiceProviderCode("<REPLACE>")
    .build();

SendRequest paymentData = new SendRequestBuilder()
    .setTo('979797')
    .setReference('11114')
    .setTransactionRef('T12344CC')
    .setAmount(10.0)
    .build();
    
client.send(paymentData).enqueue(new Callback<SendResponse> {
    @Override
    public void onResponse(Call<SendResponse> call, Response<SendResponse> response) {
        // handle success
    }
    
    @Override
    public void onFailure(Call<SendResponse> call, Throwable t) {
        // handle errors
    }
});
```

### Revert a Transaction

```java
Client client = new ClientBuilder()
    .setApiKey("<REPLACE>")
    .setPublicKey("<REPLACE>")
    .setServiceProviderCode("<REPLACE>")
    .setInitiatorIdentifier("<REPLACE>")
    .setSecurityCredential("<REPLACE>")
    .build();

ReversalRequest paymentData = new ReversalRequestBuilder()
    .setReference('11114')
    .setTransactionRef('T12344CC')
    .setAmount(10.0)
    .build();
    
client.revert(paymentData).enqueue(new Callback<ReversalResponse> {
    @Override
    public void onResponse(Call<ReversalResponse> call, Response<ReversalResponse> response) {
        // handle success
    }
    
    @Override
    public void onFailure(Call<ReversalResponse> call, Throwable t) {
        // handle errors
    }
});
```

### Query the status of a Transaction

```java
Client client = new ClientBuilder()
    .setApiKey("<REPLACE>")
    .setPublicKey("<REPLACE>")
    .setServiceProviderCode("<REPLACE>")
    .build();

QueryRequest queryData = new QueryRequestBuilder()
    .setReference('11114') // input_ThirdPartyReference
    .setSubject('T12344CC') // input_QueryReference
    .build();
    
client.query(queryData).enqueue(new Callback<QueryResponse> {
    @Override
    public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
        // handle success
    }
    
    @Override
    public void onFailure(Call<QueryResponse> call, Throwable t) {
        // handle errors
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
