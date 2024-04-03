# Use a base image with OpenJDK
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/*.jar /app/java_app.jar

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "java_app.jar"]
