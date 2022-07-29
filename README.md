## Introduction

This is the GitHub repository for my blogpost published on aykutbuyukkaya.codes.

You can find the exact post link [here](https://www.aykutbuyukkaya.codes/how-to-validate-passwords-with-constraints-in-java-spring/).

Password rules are as follows:

* Minimum 10 maximum 128 characters.
* At least one English uppercase letter.
* At least one English lowercase letter.
* At least one digit character.
* At least one special character.
* No whitespaces are allowed.

### Using Tools & Technologies

#### Backend
* Java 17
* Maven (4.0.0)
* Spring Boot (2.7.2 with Spring Data JPA, Spring Web, and Spring Validation)
* Passay (For password constraints)
* H2 Database (Database for test purposes)
* Lombok (To reduce boilerplate code)
* Springdoc Swagger-UI (For API testing)

These are APIs that we need to provide:

| Method   | Route                             | Parameter Type | Parameter Info                                                           | Description                                    |
|----------|-----------------------------------|----------------|--------------------------------------------------------------------------|------------------------------------------------|
| `POST`   | {{baseUrl}}/authentication/signup | Body           | {"email": "string","password": "string"}                                 | User sign up process with password validation. |
                                                                                                                             

## :package: Package With Maven

On project root file:

```console
$ mvn clean install
```

## :clipboard: Run With Java

```console
$ java -jar target/java-password-validation-example-0.0.1-SNAPSHOT.jar
```

