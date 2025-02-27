# MicroProfile generated Application

## Introduction

MicroProfile Starter has generated this MicroProfile application for you.

The generation of the executable jar file can be performed by issuing the following command

    mvn clean package

This will create an executable jar file **manufacturingTerminalBackend-wildfly.jar** within the _target_ maven folder.
This can be started by executing the following command

    java -jar target/manufacturingTerminalBackend-wildfly.jar

To launch the test page, open your browser at the following URL

    http://localhost:8080/index.html  

## Next Step

Leave the server-a server running and proceed with the instructions contained in `demo/service-b/readme.md`.

## Specification examples

By default, there is always the creation of a JAX-RS application class to define the path on which the JAX-RS endpoints
are available.

Also, a simple Hello world endpoint is created, have a look at the class **HelloController**.

More information on MicroProfile can be found [here](https://microprofile.io/)

### Config

Configuration of your application parameters.
Specification [here](https://microprofile.io/project/eclipse/microprofile-config)

The example class **ConfigTestController** shows you how to inject a configuration parameter and how you can retrieve it
programmatically.

### Open API

Exposes the information about your endpoints in the format of the OpenAPI v3 specification.
Specification [here](https://microprofile.io/project/eclipse/microprofile-open-api)

The index page contains a link to the OpenAPI information of your endpoints.

### Rest Client

A type safe invocation of HTTP rest endpoints.
Specification [here](https://microprofile.io/project/eclipse/microprofile-rest-client)

The example calls one endpoint from another JAX-RS resource where generated Rest Client is injected as CDI bean.



//TODO: QueryDSL Fluent API
