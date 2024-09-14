FROM amazoncorretto:22
LABEL author="juanrostirola"
WORKDIR /app
COPY build/libs/projeto-service-0.0.1-SNAPSHOT.jar projeto-service.jar
ENTRYPOINT  ["java", "-jar", "projeto-service.jar"]