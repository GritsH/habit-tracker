FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

RUN mkdir -p target/extracted
RUN java -Djarmode=layertools -jar target/habit-tracker-0.0.1-SNAPSHOT.jar extract --destination target/extracted

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/extracted/dependencies/ ./
COPY --from=build /app/target/extracted/spring-boot-loader/ ./
COPY --from=build /app/target/extracted/snapshot-dependencies/ ./
COPY --from=build /app/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]