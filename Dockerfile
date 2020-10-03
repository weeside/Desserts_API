FROM openjdk:8-jdk

WORKDIR ./external-api

CMD ["./gradlew", "build"]

ARG JAR_FILE=./external-api/build/libs/*.jar

COPY ${JAR_FILE} app.jar
