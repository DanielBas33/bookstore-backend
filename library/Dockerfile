FROM openjdk:17.0.1-jdk-slim

# Copy Jar file
COPY target/*.jar app.jar

# Run the Jar file
ENTRYPOINT ["java","-jar","/app.jar"]