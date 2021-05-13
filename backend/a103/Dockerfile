FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN timedatectl set-timezone Asia/Seoul
ADD ./target/a103-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]
