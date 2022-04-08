FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY target/service-registry-0.0.1-SNAPSHOT.jar service-registry-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/service-registry-0.0.1-SNAPSHOT.jar"]