FROM openjdk:14-jdk-alpine
COPY build/libs/boot-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
