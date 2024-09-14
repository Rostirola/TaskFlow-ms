FROM amazoncorretto:22
LABEL author="juanrostirola"
WORKDIR /app
COPY build/libs/log-service-0.0.1-SNAPSHOT.jar log-service.jar
ENTRYPOINT  ["java", "-jar", "log-service.jar"]