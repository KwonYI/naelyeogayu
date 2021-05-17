FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/a103-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENV TZ Asia/Seoul
ENTRYPOINT ["java","-jar","/app.jar"]
