# ==== Build stage ====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src

# Copier pom d'abord pour bénéficier du cache des deps
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copier le code et builder
COPY src ./src
RUN mvn -q package -DskipTests

# ==== Runtime stage ====
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier le jar
COPY --from=build /src/target/*.jar app.jar

# Env par défaut (surchargable au runtime)
ENV JAVA_OPTS="" \
    SERVER_PORT=8080

EXPOSE 8080

# Activer le port si Spring respecte SERVER_PORT, sinon laisser le default 8080 dans application.properties
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
