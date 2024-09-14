FROM amazoncorretto:22
LABEL author="juanrostirola"
WORKDIR /app
COPY build/libs/gateway-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT  ["java", "-jar", "gateway.jar"]