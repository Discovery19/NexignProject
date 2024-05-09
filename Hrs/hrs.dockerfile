FROM openjdk:21
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} hrs.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "hrs.jar"]