#./mvnw package && java -jar target/Deal-0.0.1-SNAPSHOT.jar

FROM openjdk:17-oracle
EXPOSE 8081
ARG JAR_FILE=target/Deal-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#docker build -t deal:0.0.1 .