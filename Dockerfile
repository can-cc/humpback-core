FROM maven:jdk11
COPY . /app
WORKDIR /app
RUN ./mvnw compile
ENTRYPOINT ["java","-jar","/app/build/libs/humpback-latest.jar"]
