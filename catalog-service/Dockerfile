FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY target/catalog-service-0.0.1-SNAPSHOT.jar catalog-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/catalog-service-0.0.1-SNAPSHOT.jar"]