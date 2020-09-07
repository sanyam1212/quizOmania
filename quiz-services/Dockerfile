FROM openjdk:8-alpine
RUN mkdir -p "quiz"
WORKDIR /quiz
COPY ./target/quiz-services-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar
