FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /supplier-service-app
COPY . .
EXPOSE 8081
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run