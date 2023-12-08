# Étape 1: Utiliser une image de base avec sbt et Java pour construire l'application
FROM mozilla/sbt:latest as build

# Copier les fichiers de l'application dans l'image
COPY . /app
WORKDIR /app

# Compiler et stager l'application
RUN sbt clean compile stage

# Étape 2: Préparer l'image d'exécution avec OpenJDK
FROM openjdk:21-jre-slim

# Copier les binaires de l'application compilée depuis l'image de build
COPY --from=build /app/target/universal/stage /app

# Définir le répertoire de travail
WORKDIR /app

# Exposer le port sur lequel l'application s'exécute
EXPOSE 9000

# Définir la variable d'environnement pour le secret Play
ENV PLAY_HTTP_SECRET_KEY=your-secret-key

# Commande pour démarrer l'application
CMD ["/app/bin/rest-api-nuit-l'info", "-Dplay.http.secret.key=$PLAY_HTTP_SECRET_KEY"]
