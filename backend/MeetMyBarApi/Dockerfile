# Étape 1 : Construction de l'application
FROM eclipse-temurin:21-jdk AS build 
LABEL name="meetmybar-api"

WORKDIR /app

# Copier les fichiers nécessaires au build
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew

# Copier le reste du projet
COPY src src

# Générer les classes OpenAPI avec des logs détaillés
RUN ./gradlew openApiGenerate --info --stacktrace

# Construire l'application
RUN ./gradlew bootJar --no-daemon -x test

# Étape 2 : Exécution de l'application avec une image plus légère
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]


