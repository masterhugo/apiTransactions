FROM openjdk:11.0-jdk-slim-stretch
ARG DEPENDENCY=target
COPY ${DEPENDENCY}/api-transactions-1.0.0-SNAPSHOT.jar /home/api-transactions-1.0.0-SNAPSHOT.jar
RUN echo $ENVIROMENTS
ENTRYPOINT   [ "java","-jar","-Dspring.profiles.active=release","/home/api-transactions-1.0.0-SNAPSHOT.jar" ]
