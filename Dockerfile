# Step 1: Use Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside container
WORKDIR /app

# Copy project files
COPY . .

# Build the application and skip tests for faster build (optional)
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK to run the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose app port (adjust if needed)
EXPOSE 8089

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
