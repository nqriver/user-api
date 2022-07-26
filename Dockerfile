FROM openjdk:17-alpine
ARG JAR_FILE=target/user-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} user-api.jar
ENTRYPOINT ["java", "-jar", "user-api.jar"]
EXPOSE 8080