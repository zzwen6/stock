FROM openjdk:8-jdk-alpine
COPY target/*.jar /app.jar
ENV ENV=prod
ENTRYPOINT ["java", "-jar", "/app.jar"]
