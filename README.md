
# SUBSCRIPTION MANAGEMENT SYSTEM

An application called Subscription Management System enables us to add new schemes, goods, customers, and subscriptions to the network. The system's users can additionally allocate offers to subscribers in addition to these functions.


## Authors

- [@dhinesh krishna](https://github.com/DhineshKrishnaM)



## Tech Stack

Framework    : Spring Boot 2.7.5 + Java 11

Data layer   : SpringDataJpa

Build system : Gradle


## Requirements
For building and running the application you need:
- [JDK 1.8]()
- [Gradle]()
## Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.sms.project.SubscriptionManagementSystemApplication.java class from your IDE.

Alternatively you can use the Spring Boot Gradle plugin like so:

[gradlew run]()
## Modules
There are several modules in Spring Boot. Here is a quick overview:

### spring-boot

The main library providing features that support the other parts of Spring Boot. These include:

The SpringApplication class, providing static convenience methods that can be used to write a stand-alone Spring Application. Its sole job is to create and refresh an appropriate Spring ApplicationContext.

Embedded web applications with a choice of container Tomcat.

First-class externalized configuration support.

Convenience ApplicationContext initializers, including support for sensible logging defaults.

## Liquibase

Liquibase makes it easy to define database changes in a format that's familiar and comfortable to each user and then automatically generates database-specific MySQL for you. Liquibase uses changesets to represent a single change to your database.

## OpenAPI (Swagger 3.0.3)

An OpenAPI definition can then be used by documentation generation tools to display the API, code generation tools to generate servers and clients in various programming languages, testing tools, and many other use cases.
