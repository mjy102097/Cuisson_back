FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/*.jar
ARG PROFILES
ARG ENV
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.prifiles.active=${PROFILES}", "-jar", "app.jar"]