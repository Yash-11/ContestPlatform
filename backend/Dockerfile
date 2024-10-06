# FROM eclipse-temurin:17-jdk-alpine
# VOLUME /tmp
# COPY target/contestplatform-0.0.1-SNAPSHOT.jar contestplatform.jar
# ENTRYPOINT ["java","-jar","/contestplatform.jar"]
# EXPOSE 8080

FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
RUN apk add --no-cache docker-cli
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]