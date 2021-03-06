FROM gradle:7.3.2-jdk11-alpine AS build
ENV APP_HOME=/root/dev/topscore
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
COPY build.gradle gradlew gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY . .
RUN ./gradlew test
RUN ./gradlew iT
RUN ./gradlew build


FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=build /root/dev/topscore/build/libs/topscore-0.0.1-SNAPSHOT.jar topscore.jar
EXPOSE 8080
CMD ["java", "-jar", "./topscore.jar"]