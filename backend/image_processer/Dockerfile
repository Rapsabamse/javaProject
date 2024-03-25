# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:17-jdk-jammy as deps

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Copy the Gradle wrapper files into the container
COPY --chmod=0755 gradlew .
COPY gradle/ gradle/


# List the contents of the directory for debugging purposes
RUN ls -l /app

# Ensure the Gradle wrapper has executable permissions
RUN chmod +x gradlew


# Run the Gradle bootRun command
CMD ["./gradlew", "bootRun"]
