FROM openjdk:17-jdk-alpine
RUN apk update && apk add --no-cache bash
ARG JAR_FILE=build/libs/*.jar
RUN gradle build
ARG PORT
ENV PORT=${PORT}
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]
