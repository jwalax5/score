FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ADD build/libs/topscore-0.0.1-SNAPSHOT.jar topscore.jar
ENTRYPOINT ["java", "-jar", "./topscore.jar"]