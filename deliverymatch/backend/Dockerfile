FROM openjdk:17-jdk-alpine
#copy the jar file
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
#run the app
ENTRYPOINT ["java","-jar","/app.jar"]