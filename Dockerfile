FROM openjdk:17-jdk-alpine
COPY ./build/libs/scoring-service-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java","-jar","scoring-service-0.0.1-SNAPSHOT.jar"]