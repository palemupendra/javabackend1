# # Step 1: Use Maven image to build the application
# FROM maven:3.9.6-eclipse-temurin-17 AS builder

# # Set working directory inside container
# WORKDIR /app

# # Copy project files
# COPY . .

# # Build the application and skip tests for faster build (optional)
# RUN mvn clean package -DskipTests

# # Step 2: Use a lightweight JDK to run the app
# FROM eclipse-temurin:17-jdk-alpine

# # Set working directory
# WORKDIR /app

# # Copy built jar from builder stage
# COPY --from=builder /app/target/*.jar app.jar

# # Expose app port (adjust if needed)
# EXPOSE 8089

# # Run the application
# ENTRYPOINT ["java", "-jar", "app.jar"]

# ===============================
# Stage 1: Build Stage
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy only pom first (better caching)
COPY pom.xml .

# Download dependencies first (cached layer)
RUN mvn -B -q dependency:go-offline

# Copy source code
COPY src ./src

# Build jar
RUN mvn -B clean package -DskipTests


# ===============================
# Stage 2: Runtime Stage
# ===============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Security best practice (run as non-root)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose internal container port
EXPOSE 8089

# Optional JVM tuning
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
