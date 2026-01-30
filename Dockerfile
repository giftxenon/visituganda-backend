FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy all project files
COPY . .

# Give execute permission to mvnw (Linux requirement)
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (Render uses this)
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/visituganda-0.0.1-SNAPSHOT.jar"]
