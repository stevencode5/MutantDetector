# Mutant Detector

This service detects if a human is a mutant based on its DNA sequence.
It receives as a param an array of Strings in Json format that will represent each row of a table of NxN with the DNA sequence. 
The letters of the Strings can only be (A,T,C,G), which represents each the nitrogenous base of DNA.
A human is a mutant if more than one sequence of four letters equals, obliquely, horizontally, or vertically are found.

## Tech Stack
* Language: [OpenJDK 11](https://adoptopenjdk.net/) 
* Framework: [Spring Boot](https://spring.io/projects/spring-boot)
* Database: [MySQL](https://dev.mysql.com/doc/)
* Language tool: [Project Lombok](https://projectlombok.org/)
* Database migration: [Flyway](https://flywaydb.org/)
* Logging: [Slf4j(Lombok)](https://projectlombok.org/api/lombok/extern/slf4j/Slf4j.html)
* Unit testing: [Junit](https://junit.org/) [JMockit](https://jmockit.github.io/)
* Deployment: [Docker](https://docs.docker.com/) 
* IDE: [IntelliJ IDEA](https://www.jetbrains.com/idea/)


## Prerequisites
* [OpenJDK 11](https://adoptopenjdk.net/)
* [MySQL](hhttps://www.mysql.com/downloads/) 

## Start app
* Create a local database manually or using the [create_mutant_db.sh](create_mutant_db.sh) script that launch a MySql docker container
* Set the Environment variables with the database connection details. SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD. 
* If the script was used then you can run:
```sh
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3326/mutant
export SPRING_DATASOURCE_USERNAME=mutant
export SPRING_DATASOURCE_PASSWORD=mutantpass 
 ```
* Build the application using the command:
```sh
./gradlew clean build
```
* Start the app using the command:
```sh
./gradlew bootRun
```
* The app runs on [http://localhost:8080](http://localhost:8080/stats)

## Test services using [Curl](https://curl.se/)
* Execute to test /dna service
```sh
curl --request POST 'localhost:8080/mutant' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dna": [
        "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"
    ]
}'
```
* Execute to test /stats service
```sh
curl --request GET 'http://localhost:8080/stats'
```

## Unit test
* Run all tests
```sh
./gradlew test
```

## Code coverage report
* Execute Jacoco task to generate the code coverage report
```sh
./gradlew jacocoTestReport
```
* After the execution, the report is saved in [build/reports/jacoco/test/html/index.html](build/reports/jacoco/test/html/index.html)
