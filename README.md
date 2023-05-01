<h1 align="center"> Engineering Home Task </h1> <br>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
- [Business Logic](#business-logic)
- [API](#API)
- [Technical Stack](#tech-stack)
- [Build Process](#build-process)
- [TODO](#todo)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction
This is Sping Boot project that evaluate 'velocity limits'.
It provides REST API to upload a txt-file containing fund loads to verify if fund load passes daily/weekly limits.
The response contains txt-file with fund loading results.

## Technical Stack
To implement API the following stack was used:
- Java 17
- Spring Boot 3
- Build tool: Maven 
- In-Memory Database: H2

## Business Logic
In finance, it's common for accounts to have so-called "velocity limits". In this task, you'll
create a Java Spring boot application that accepts or declines attempts to load funds
into customers' accounts in real-time.
Currently, there are 2 period limits:
 - daily limit: 5000.00 and 3 loads
 - weekly limit (Mon-Sun): 20000.00

Limit values can be changed through database table.

## API
Single POST-endpoint to upload a file
`<host>/upload`

- Example of format for uploading file :
  ``
  {"id":"10285","customer_id":"171","load_amount":"$4961.88","time":"2000-01-17T21:01:12Z"}
  {"id":"7558","customer_id":"800","load_amount":"$3680.19","time":"2000-01-17T22:02:34Z"}
  ...
  ``
- Example of format for downloading file:

  ``
  {"id":"10694","customer_id":"1","accepted":true}
  {"id":"15089","customer_id":"205","accepted":false}
  ...
  ``


## Build Process
To build the project, run the maven command:
```shell
mvn clean package
```

To run tests, use the command:
```shell
mvn clean test
```

To run the application use the command or your IDE:
```shell
mvn spring-boot:run
```


