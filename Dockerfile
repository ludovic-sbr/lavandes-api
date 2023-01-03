FROM maven:3.8.6 AS BUILDER

# Copie du projet dans le workdir
WORKDIR /app
COPY . /app

# build de l'app
RUN mvn package

FROM eclipse-temurin:17-jdk-alpine as RUNNER

EXPOSE 8000

# récupération du .jar
ARG JAR_FILE=lavandes-backend-1.0.0.jar

WORKDIR /opt/app

COPY --from=BUILDER /app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","lavandes-backend-1.0.0.jar"]