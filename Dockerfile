FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /home/app
COPY the-knife/src /home/app/src
COPY the-knife/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:21-alpine

COPY --from=build /home/app/target/spring-the-knife-0.0.1-SNAPSHOT.jar /app/spring-the-knife-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/spring-the-knife-0.0.1-SNAPSHOT.jar"]