FROM eclipse-temurin:21-jdk-alpine
RUN mkdir /opt/app
COPY application/target/application-0.0.1-SNAPSHOT.jar /opt/app/application.jar
WORKDIR /opt/app
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080