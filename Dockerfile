FROM openjdk:17-jdk-alpine
RUN apk update && apk add --no-cache bash
ARG JAR_FILE=app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]