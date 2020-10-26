FROM java:8

EXPOSE 8082

ADD target/courierService-1.0-SNAPSHOT.jar courierService-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "courierService-1.0-SNAPSHOT.jar"]