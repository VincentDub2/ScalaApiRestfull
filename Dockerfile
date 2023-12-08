# Étape 1 : Utiliser une image de base avec SBT pour construire l'application
FROM hseeberger/scala-sbt:11.0.13_1.5.5_2.13.7 as builder

# Exposer le port sur lequel votre application Scala Play écoute (par défaut 9000)
EXPOSE 9000

RUN sbt run

