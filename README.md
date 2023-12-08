# 🌟 REST API Nuit de l'Info

Ceci est un projet d'API REST développé en Scala avec Play Framework lors de la Nuit de l'Info, un événement national.

### 📚 Présentation

1. Outils et Environnement de Développement :
   Nous avons utilisé IntelliJ IDEA pour son efficacité et ses fonctionnalités avancées, et sbt pour la gestion de projet et des dépendances. Notre projet intègre Play Framework, avec des dépendances clés comme Guice, Joda-Convert, Scala-URI, et Scala-Guice, ainsi que Logstash-Logback-Encoder pour la journalisation.

2. Patterns de Programmation Fonctionnelle :
   Nous avons adopté des patterns de programmation fonctionnelle, tels que l'utilisation de traits pour une composition modulaire, la composition fonctionnelle, et l'emploi d'implicits pour simplifier le code. Les fonctions de haut niveau ont été utilisées pour un style déclaratif, et la gestion des effets secondaires a été réalisée via ExecutionContext et Future, mettant en valeur l'immutabilité et les opérations non-bloquantes.

3. Retour d'Expérience :
   Transitionner vers Scala après avoir travaillé avec Node.js a été un processus d'apprentissage. Nous avons facilement adopté des principes comme l'immutabilité et les fonctions pures, mais nous avons rencontré des difficultés avec des aspects plus complexes de Scala, tels que la programmation asynchrone et l'utilisation des implicits. Notre expérience en C, en particulier dans le développement d'une messagerie, a facilité la compréhension de la programmation synchrone.

### 🚀 Démarrage

Pour exécuter cette application, vous devez télécharger et installer sbt.

Une fois sbt installé, la commande suivante dans l'invite de commande démarre Play en mode développement :

```bash
sbt run
```

Play se lancera sur le port HTTP à l'adresse <http://localhost:9000/>. Vous n'avez pas besoin de déployer ou de recharger quoi que ce soit -- tout changement du code source pendant que le serveur fonctionne recompilera et rechargera automatiquement l'application à la prochaine requête HTTP.

### 📝 Utilisation

Les routes disponibles sont :

- `GET /api/item\` : Pour récupérer tous les items.
- `GET /api/item/id/{id}\` : Pour récupérer un item spécifique par son ID.
- `GET /api/item/name/{name}\` : Pour récupérer un item spécifique par son nom.

Si vous appelez la même URL depuis la ligne de commande, vous verrez du JSON. En utilisant [httpie](https://httpie.org/), nous pouvons exécuter la commande :

```bash
http --verbose http://localhost:9000/api/item
```

et obtenir en retour :

```routes
GET /api/item HTTP/1.1
```


## 📚 Documentation

Cette api a était fait avec Play Framework, vous pouvez retrouver la documentation ici : https://www.playframework.com/documentation/2.8.x/Home
Elle est construite grace au "template" play-scala-rest-api-example :
https://github.com/playframework/play-samples/
