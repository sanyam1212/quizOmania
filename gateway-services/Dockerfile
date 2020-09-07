FROM openjdk:8-alpine
RUN mkdir -p "gateway-ui"
WORKDIR /gateway-ui
COPY ./target/gateway-services-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar
