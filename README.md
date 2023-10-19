# Spring Boot Example

`/GET /POST /PUT /DELETE` APIs for managing donations.

## Tech stack

* Java 17
* Spring boot - Create and read operations for managing donations; application exposed on port `8081`
* Spring boot security - basic authentication applied to all APIs
* H2 database - for testing code 
* Swagger
* OpenApi
* Wiremock - for integration tests - stubbing the external URL from where donations database is populated on application startup (see `src/test/resources/mappings/donations.json` file)
* Spring integration tests using `WebTestClient`
* Junit5, AssertJ
* Maven
* Docker
* Github Actions - for CI/CD, see `.github` folder and `Actions` tab on this page

## Prerequisites

In order to build the project, you will have to install the following:

* Java 17
* Maven
* Docker (optional)
* This project includes **Lombok Annotations**, this means that in order for your IDE to correctly compile your project you'll need to add the Lombok plugin to your IDE and `Enable annotation processing` (for IntelliJ IDEA).


## Build

### Maven

```
mvn clean install
```

### Docker

```
docker build -t donation-service .
```

## Run

### Maven

```
mvn spring-boot:run
```

### Docker

```
docker run -p 8081:8081 donation-service
```


## Swagger / OpenApi

Swagger endpoint: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

OpenApi endpoint: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

_**Important**_: swagger/openapi dependency for spring-boot 3 is now `springdoc-openapi-starter-webmvc-ui`  

## APIs

All APIs are secured using basic auth. Use the following credentials when making requests:
```
username=donation-user
password=donation-password-which-should-be-kept-in-a-secret-place-and-injected-when-application-is-deployed
```

* GET /donations
```
curl 'localhost:8081/donations' \
-u "donation-user:donation-password-which-should-be-kept-in-a-secret-place-and-injected-when-application-is-deployed"
```

* GET /donations/{donation_id}
```
curl 'localhost:8081/donations/15' \
-u "donation-user:donation-password-which-should-be-kept-in-a-secret-place-and-injected-when-application-is-deployed"
```

* POST /donations
```
curl -X POST 'localhost:8081/donations' \
-u "donation-user:donation-password-which-should-be-kept-in-a-secret-place-and-injected-when-application-is-deployed" \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "some title",
    "description": "some description"
}'
```



## Notes:

* The `donationDatabasePopulatorTest` integration test is using Wiremock to stub the external URL for loading donations. The file mapping of that response is located under `src/test/resources/mappings/donations.json`.
