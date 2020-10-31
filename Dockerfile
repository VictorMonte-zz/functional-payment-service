FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/functional-payment-service-0.0.1-SNAPSHOT-standalone.jar /functional-payment-service/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/functional-payment-service/app.jar"]
