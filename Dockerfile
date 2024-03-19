FROM adoptopenjdk:17-jdk-hotspot

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
