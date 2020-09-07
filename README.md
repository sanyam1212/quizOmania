# quizOmania
This project offers a utility for generating open-quizzes and sharing(via url) them for educational & training purposes.
This includes:
Eureka-Server: which acts as the registry for the microservices, i.e it registers the instance info of all the microservices running on the box, which primarily     includes the IP, port and APIs served by it.
MariaDB: acts as the data-storage utility, and its open-source and easy to use.
Quiz-services: Spring Boot based microservice which is serving the APIs for this platform. At starting it registers itself with the eureka server and it also connects with mariaDB for data persistence.
Gateway: single endpoint through which all the APIs are being served, it connects with the eureka server via eureka-discovery-client which provides the instance info off all the microservices running in the app. In this case the only microservices is quiz-service.
