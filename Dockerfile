FROM openjdk:8-jdk

ARG module
ARG JAR_FILE="./$module/build/libs/*.jar"

COPY ${JAR_FILE} app.jar
