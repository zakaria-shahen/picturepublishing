FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} picturepublishing.jar
ENTRYPOINT ["java", "-jar", "picturepublishing.jar"]

