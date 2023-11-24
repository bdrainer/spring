# Reactive MongoDB

Spring Webflux application using MongoDB.

## Java 21 and GraalVM

The project is using Java 21.  I am using JDK `21-graalce` running on a Mac.  

I use [SDKMan](https://sdkman.io/) to install and manage Java versions:  `sdk install java 21-graalce`.

## MongoDB

The `docker-compose.yml`, in the project root, can be used to start MongoDB.  Consider adjusting the `volumes` to meet 
your needs.

The application connects to MongoDB with these properties:
* host: localhost
* port: 27017
* database: test

Adjust these values in  `src/main/resources/application.yml` to match your environment, or override these values using
environment variables / system variables.


## Application Design

The application manages profiles. 

There are three profile types: 
* ADDRESS
* CONTACT
* EMPLOYEMENT

There is a REST API for saving and retrieving profiles.  The MongoDB collection `Profiles` is where the data is saved.

When saving a profile you need to provide
* username - this is the identifier for all you profiles, an example is `johnsmith`
* profile type - this needs to be one of the three types listed above
* userkey - pick any name for the profile you are creating, it creates uniqueness within a profile type, you might have more than one address so you could name them home, shipping, billing, etc.

## REST API

The application by default runs on port 8080.  The REST API resource path is `/profiles`

### Saving A Profile

Saving a profile requires a username in the path and a Profile JSON as the request body.

PUT `http://localhost:8080/profiles/{username}`

Example Contact Profile:
```json
{
    "type" : "CONTACT",
    "userkey" : "primary",
    "firstname" : "Hal",
    "lastname" : "Joran",
    "email" : "haljordan@gmail.com",
    "phone" : "800-555-1234"
}
```


### Getting A Profile

There are three ways to get a profile

1. Get a specific profile: GET `http://localhost:8080/profiles/{username}/{profile-type}/{userkey}`
2. Get a list of profiles for a specific profile type: GET `http://localhost:8080/profiles/{username}/{profile-type}`
3. Get a list of profiles for a user: GET `http://localhost:8080/profiles/{username}`

### Postman Collection

There is a Postman collection in the root of the project.  It has different requests you can use to test with.
