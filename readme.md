Exchange Rates
==

# The assignment
Develop a service, that constantly checks the currency exchange rate from Euro to US-Dollar (1 EUR = x USD).

## Requirements:
* The check period has to be configurable
* The results are stored in a database. The database access does not need to be fully implemented, an interface is sufficient.
* The service has an HTTP-Resource with the following endpoints (The protocol design is up to you):
    * Get the latest rate
    * Get historical rates from startDate to endDate
* Please ensure the functionality of the business logic using unit-tests
* The exchange rate can be taken from a public service or be mocked.
* The project should be managed with maven and the tests have to be executable using 'mvn test'.
* Please describe in a few sentences and/or with a diagram how you planned the project and architecture.

# Stack

* Java 8
* Spring Boot 2
* Lombok
* Swagger 2
* MongoDB
* Docker
* Docker Compose

# Configuring

This project uses the [Fixer.io](https://fixer.io/) service to get the exchange rates. To run the project you must need to add your API key in the `application.yml`.

Replace the content *YOUR API KEY GOES HERE* with your key.

To change the retrieve period of the exchange rate from the Fixer API, you have to change the property `fixer.cron` on the `application.yml`. By default, the project will retrieve the exchange rates from Monday to Friday, every minute from 8 AM to 18 PM. 

# Building
This project uses Maven for project management tool. To build and package the project, the following command must be executed:

```
$ mvn clean package
```

The following command will package the project, but skipping the tests

```
$ mvn clean package -DskipTests
```

# Running

Although the project uses the [Fongo](https://github.com/fakemongo/fongo) database to run the tests, to run in a "production" environment you must have the MongoDB running on port "27017" and a user/password defined as "root"/"password".

This project provides a `docker-compose` file within the required pre-configured external components, so to before to run the project you must run the following command:

```
$ docker-compose up
```

After the required components are up, you should be able to start the application using the following command:

```
java -jar searchmetrics-exchange-rates.jar
```

## Running using Docker Compose

The following command will start the application as well as the required components.

```
$ docker-compose -f docker-compose.yml -f docker-compose.app.yml up
```

# Known issues

* This project creates the Swagger API Documentation at runtime using a third-party framework, and as a known issue, on the documentation is showing some response codes that actually, is not a valid response.
