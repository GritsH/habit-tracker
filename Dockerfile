FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

RUN java -jar habit-tracker-server/target/habit-tracker-server-0.0.1-SNAPSHOT.jar --version || echo "Checking JAR..."

RUN cd habit-tracker-server/target && java -Djarmode=layertools -jar habit-tracker-server-0.0.1-SNAPSHOT.jar extract --destination extracted

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/habit-tracker-server/target/extracted/dependencies/ ./
COPY --from=build /app/habit-tracker-server/target/extracted/spring-boot-loader/ ./
COPY --from=build /app/habit-tracker-server/target/extracted/snapshot-dependencies/ ./
COPY --from=build /app/habit-tracker-server/target/extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]