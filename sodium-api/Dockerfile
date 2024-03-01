FROM maven:3.9.5-eclipse-temurin-21 as build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:21-jdk-slim

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]