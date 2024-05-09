FROM openjdk:21
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} crm.jar
#ADD ./target/*.jar crm.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "crm.jar"]
#ENTRYPOINT java -jar crm.jar