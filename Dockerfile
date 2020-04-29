FROM gradle:jdk11
COPY . /app
WORKDIR /app
RUN ./gradlew build -x test
ENTRYPOINT ["java","-jar","/app/build/libs/humpback-latest.jar"]
