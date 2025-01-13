FROM alpine/java:21-jdk

ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests

EXPOSE 8081

LABEL authors="Ivanbyone"

ENTRYPOINT ["java", "-jar", "target/cards-aggregator-0.0.1.jar"]