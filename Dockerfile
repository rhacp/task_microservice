# Start with a base image containing Java Alpine
FROM openjdk:17-jdk-alpine

# The application's jar file
ARG JAR_FILE=target/task-microservice-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY target/task-microservice-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]