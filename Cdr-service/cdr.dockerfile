FROM openjdk:21

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} cdr.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "cdr.jar"]