FROM eclipse-temurin:17-jre-alpine
EXPOSE 8082
COPY target/poststeadpostservice-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
