# Kafka Streams

Multi-module Gradle project using Kafka and Spring Cloud Stream.

## Overview

The project contains 3 services.  Each service has its configuration defined in its respective application.yml.

Spring Cloud Stream makes it easy to publish and consume to/from Kafka topics.  There was minimal code required to
accomplish this.  Each service has a `Bean` that defines the publisher, processor, and consumer.  


### kafka-producer

Produces a continuous stream of integers every second.  The range of integers is 1 to 200.  The topic the stream is
published to is named `numbers`.

The producer service runs on port 8080.

### kafka-processor

Consumes messages from the `numbers` topics, filtering out odd values and leaving only even values.  The even 
values are squared and published to a topic called `squaredNumbers`.

The processor service runs on port 8082.

### kafka-consumer

Consumes messages from the `squaredNumbers` topic and prints them to the console.

The processor service runs on port 8081.
 

## Running Kafka Locally

To run the 3 services in this application you only need to run Kafka (i.e. the broker).

The `docker-compose.yml` in the `docker` folder contains the Confluent suite of applications for Kafka.  You could run 
all containers and test out what they offer.  See the quickstart link below.

To start the Kafka container run `docker-compose up -d broker`.  This starts Kafka and exposes it on port `9092`.

## References

For further reference, please consider the following sections:

[Confluent quickstart](https://docs.confluent.io/platform/current/platform-quickstart.html)