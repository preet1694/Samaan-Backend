# ===================== Build Stage =====================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven Wrapper and project files
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Grant execute permission to Maven Wrapper
RUN chmod +x mvnw

# Download dependencies (improves caching)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# ===================== Runtime Stage =====================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (Render automatically assigns it)
EXPOSE 8080

# Run the application with Render-friendly settings
CMD ["java", "-jar", "app.jar", "--server.port=${PORT}"]
