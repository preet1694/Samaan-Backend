# Use JDK to build the application
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy the source code and Maven files
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src src

# Grant execute permission to mvnw
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Use JRE for the final image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
