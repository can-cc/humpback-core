FROM maven:3.6.3-jdk-14
COPY . /app
WORKDIR /app
RUN mvn package
ENTRYPOINT ["java","-jar","/app/target/humpback-latest.jar"]
