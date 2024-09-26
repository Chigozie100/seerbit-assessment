FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/seerbit.assessment-0.0.1-SNAPSHOT.jar seerbit.assessment-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/seerbit.assessment-0.0.1-SNAPSHOT.jar"]
