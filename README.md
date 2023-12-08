# 🌟 REST API Nuit de l'Info

Ceci est un projet d'API REST développé en Scala avec Play Framework lors de la Nuit de l'Info, un événement national.

## 📚 Sommaire

### 🚀 Démarrage

Pour exécuter cette application, vous devez télécharger et installer sbt.

Une fois sbt installé, la commande suivante dans l'invite de commande démarre Play en mode développement :

```bash
sbt run
```

Play se lancera sur le port HTTP à l'adresse \<http://localhost:9000/\>. Vous n'avez pas besoin de déployer ou de recharger quoi que ce soit -- tout changement du code source pendant que le serveur fonctionne recompilera et rechargera automatiquement l'application à la prochaine requête HTTP.

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

