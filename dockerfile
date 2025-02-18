FROM amazoncorretto:17-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM amazoncorretto:17-alpine
COPY --from=build /workspace/app/target/*.jar /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]