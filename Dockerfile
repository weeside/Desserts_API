FROM openjdk:8-jdk

ARG JAR_FILE=./external-api/build/libs/*.jar

COPY ${JAR_FILE} app.jar
