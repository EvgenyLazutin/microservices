FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/inventory-service-0.0.1-SNAPSHOT.jar"]