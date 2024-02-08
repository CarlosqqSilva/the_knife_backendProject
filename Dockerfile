FROM eclipse-temurin:21-alpine

WORKDIR /app

COPY the-knife/target/spring-the-knife-0.0.1-SNAPSHOT.jar /app/spring-the-knife-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "spring-the-knife-0.0.1-SNAPSHOT.jar"]