# Use Eclipse Temurin JDK 21 as base image
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src

# Grant execute permission to Maven wrapper
RUN chmod +x mvnw

# Build the JAR file
RUN ./mvnw clean package -DskipTests

# Use a minimal JDK runtime for running the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/samaan-1.0.0.jar app.jar

# Expose the application's port (change if necessary)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
