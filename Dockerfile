FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/seerbit.assessment-0.0.1-SNAPSHOT.jar seerbit.assessment-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/seerbit.assessment-0.0.1-SNAPSHOT.jar"]
