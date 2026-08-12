FROM openjdk:26-ea-7-jdk-bullseye
#FROM openjdk:26-ea-jdk-slim

# MAINTAINER instruction is deprecated in favor of using label
LABEL "org.opencontainers.image.authors"="sihibank.com"

# Add the application's jar to the image
COPY target/configserver-0.0.1-SNAPSHOT.jar configserver-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "configserver-0.0.1-SNAPSHOT.jar"]