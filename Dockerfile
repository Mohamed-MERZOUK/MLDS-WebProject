FROM java:8
Expose 8082
ADD /build/libs/webProject-0.0.1-SNAPSHOT.jar webProject-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "webProject-0.0.1-SNAPSHOT.jar"]
