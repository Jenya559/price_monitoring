
## Build stage
FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
COPY ./ ./
RUN mvn  clean package
## Package stage
FROM openjdk:11.0.13-slim
COPY --from=MAVEN_BUILD /target/price-monitoring.jar /price-monitoring.jar
ENTRYPOINT ["java","-jar","/price-monitoring.jar"]

#FROM openjdk:11
#ADD target/price-monitoring.jar price-monitoring.jar
#ENTRYPOINT ["java","-jar","price-monitoring.jar"]



