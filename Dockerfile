FROM openjdk:11.0-jdk-slim-stretch

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Set the name of the application's jar file
ARG JAR_FILE=mutant-detector-latest.jar

# Set the path to the application's jar file
ARG JAR_PATH="build/libs/${JAR_FILE}"
ADD ${JAR_PATH} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "mutant-detector-latest.jar"]