FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/assignment-*.jar assignment.jar
ENTRYPOINT ["java", "-jar", "assignment.jar"]