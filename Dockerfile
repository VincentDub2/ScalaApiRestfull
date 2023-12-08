# Étape 1 : Utiliser une image de base avec SBT pour construire l'application
FROM hseeberger/scala-sbt:11.0.13_1.5.5_2.13.7 as builder

# Créer un répertoire de travail
WORKDIR /app

# Copier les fichiers de votre application (build.sbt, project/, app/, conf/, etc.)
COPY . .

# Exécuter la tâche de construction de votre application Scala Play
RUN sbt stage

# Étape 2 : Utiliser une image légère comme image finale
FROM openjdk:11-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le résultat de la construction de l'étape précédente dans cette étape
COPY --from=builder /app/target/universal/stage /app

# Exposer le port sur lequel votre application Scala Play écoute (par défaut 9000)
EXPOSE 9000

RUN sbt run

