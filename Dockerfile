FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy project files
COPY . .

# Allow mvnw to run
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Koyeb uses dynamic port
EXPOSE 8080

# Run Spring Boot
CMD ["java", "-jar", "target/visituganda-0.0.1-SNAPSHOT.jar"]