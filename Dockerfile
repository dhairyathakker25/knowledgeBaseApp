#Use exiting image for JDK 8
FROM adoptopenjdk/openjdk8:x86_64-alpine-jdk8u222-b10-slim
VOLUME /tmp
ADD build/libs/knowledgeBase-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=default", "-jar", "/app.jar"]