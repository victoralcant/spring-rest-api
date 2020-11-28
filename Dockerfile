FROM adoptopenjdk/openjdk11:ubi
RUN mkdir /opt/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app.jar"]