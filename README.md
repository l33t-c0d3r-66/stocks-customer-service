## Introduction
The Customer Service is a key component of the larger Spring WebFlux project. It is responsible for managing customer portfolio information, including customer details, account balance, and the stocks owned by the customer. This service is built using Spring WebFlux to take advantage of reactive programming for handling a high volume of concurrent requests efficiently.

## What is Reactive Programming?
Reactive Programming is a paradigm that deals with asynchronous data streams and the propagation of changes. It allows applications to be highly responsive, scalable, and resilient. Reactive systems handle backpressure, which is crucial for maintaining performance under load.

## What is Spring Boot?
Spring Boot is a framework that simplifies the development of Spring-based applications by providing a set of conventions and tools. It allows developers to create stand-alone, production-grade Spring applications with minimal configuration.

## What is Spring WebFlux?
Spring WebFlux is a reactive web framework introduced in Spring 5. It is fully non-blocking and supports Reactive Streams backpressure. WebFlux is designed to handle large volumes of requests asynchronously, making it an ideal choice for modern web applications that require high performance and scalability.

## Advantages of Spring WebFlux
<b> Non-Blocking I/O:</b> Handles a large number of concurrent requests without blocking the threads.
<b> Backpressure Handling:</b> Supports Reactive Streams, ensuring that producers and consumers operate efficiently without overwhelming each other.
<b> Scalability:</b> Perfect for applications requiring high throughput with minimal resource consumption.

## Communication with Aggregator Service
The Aggregator Service interacts with the Customer Service to retrieve customer portfolio information, including customer details, account balances, and the stocks they own. The communication is asynchronous and reactive, allowing the system to scale efficiently even under heavy load.

