# First stage: Build the application
FROM maven:3.8.3-openjdk-17-slim AS build

# Set the working directory in the builder container
WORKDIR /app

# Copy the pom.xml (and optionally the settings.xml if you have one)
COPY pom.xml .

# Download dependencies as a separate step to take advantage of Docker layer caching
RUN mvn dependency:go-offline -B

# Copy your source code
COPY src src

# Package the application without running tests
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged jar from the build container
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
