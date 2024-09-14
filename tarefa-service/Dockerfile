FROM amazoncorretto:22
LABEL author="juanrostirola"
WORKDIR /app
COPY build/libs/tarefa-service-0.0.1-SNAPSHOT.jar tarefa-service.jar
ENTRYPOINT  ["java", "-jar", "tarefa-service.jar"]