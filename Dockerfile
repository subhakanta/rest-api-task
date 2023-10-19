FROM eclipse-temurin:17.0.5_8-jdk-focal

COPY target/Donation-service-0.0.1-SNAPSHOT.jar Donation-service.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/Donation-service.jar"]
