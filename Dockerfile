FROM eclipse-temurin:17-jre-alpine
EXPOSE 8082
COPY target/poststeadpostservice-1.0.0.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
