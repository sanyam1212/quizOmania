FROM openjdk:8-alpine
RUN mkdir -p "eureka"
WORKDIR /eureka
COPY ./target/eureka-server-v1.jar app.jar
CMD java -jar app.jar
