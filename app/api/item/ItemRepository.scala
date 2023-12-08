package api.item
import model.Item
import play.api.i18n.Lang.logger

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

// Vous devez importer votre système de gestion de base de données et le contexte d'exécution ici

class ItemRepository @Inject()(implicit ec: ExecutionContext) {
  // Simuler une base de données en mémoire
  private var items: List[Item] = List(
    Item("1", "Jean", "24,56 kg CO2e / unité Valeurs exprimées en kg CO2e émis par produit comprenant la fabrication, la distribution et l’usage. La production de jeans a un bilan carbone élevé en raison de la culture intensive du coton, de la teinture, de la consommation d'eau et de l'énergie dans la fabrication, ainsi que du transport. Opter pour des jeans durables, recyclés ou de seconde main et réduire la surconsommation de vêtements peut contribuer à atténuer leur impact environnemental.", +80, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701994633/njqh5jahq6vour3govi9.png"),
    Item("2", "Arbre", "Absorbe environ 25 kg de CO2 par an. Offrant une ombre protectrice et un effet rafraîchissant", -70, "url-to-image-2"),
    Item("3", "TGV", "0,002 kg CO2e / km. Un TGV rejette en moyenne 1,7g par km. C'est entre 82g et 107g par km pour une voiture qui n'est pas électrique - en fonction des modèles - et entre 73g et 250 g par km pour l’avion. Prenons un exemple concert : un voyage de Paris à Marseille. Si, en TGV, les émissions de CO2 pour chaque passager est de 1,7 kg par kilomètre, la voiture en rejettera, elle, 127. C'est donc 74 fois plus que le train avec un seul voyageur.", -20, "url-to-image-2"),
    Item("4", "climatisation", "413,17 kg CO2e / unité, Valeurs exprimées en kg CO2e émis par produit comprenant la fabrication, la distribution et l’usage. En même temps qu’elle refroidit l’air intérieur, réchauffe l’air extérieur.", +200, "url-to-image-2"),
    Item("5", "Voiture", "0,22 kg CO2e / km , Valeurs exprimées en kg CO2e émis par personne en France. Sont incluses les émissions directes, la construction des véhicules (fabrication, maintenance et fin de vie) et la production et distribution de carburant et d'électricité. La construction des infrastructures (routes, rails, aéroports...) n'est pas incluse. Les voitures personnelles sont l'un des principaux pollueurs puisqu’elles représentent 60,6 % des émissions totales de CO2 dues au transport routier en Europe.", +150, "url-to-image-2"),
    Item("6", "Viande steak", "7,26 kg CO2e / repas Valeurs exprimées en kg CO2e émis par repas comprenant la fabrication, la distribution et la consommation. La production de viande, en particulier de bœuf, a un bilan carbone élevé en raison de l'élevage intensif, de l'alimentation des animaux, du transport, de la déforestation et de la gestion des déchets. Réduire la consommation de viande et soutenir des pratiques durables sont des moyens efficaces de réduire l'impact environnemental.", +50, "url-to-image-2"),
    Item("7", "Smartphone", "29,24 kg CO2e / unité Valeurs exprimées en kg CO2e émis par produit comprenant la fabrication, la distribution et l'usage. La production de smartphones a un bilan carbone élevé en raison de l'extraction des matériaux, de la fabrication, de la consommation d'énergie et du transport. Prolonger la durée de vie des téléphones, recycler les anciens modèles et choisir des appareils écoénergétiques sont des moyens de réduire leur impact environnemental.", +100, "url-to-image-2"),
    Item("8", "Repas végétalien", "0,51 kg CO2e / repas. Les repas végétaliens ont généralement une empreinte carbone plus faible que les repas contenant de la viande en raison de l'absence d'élevage animal. Cependant, l'empreinte carbone d'un repas végétalien dépend des aliments spécifiques choisis et de leur mode de production. Opter pour des aliments locaux, de saison, cultivés de manière durable et peu transformés peut réduire davantage l'impact environnemental de votre repas végétalien.", -20, "url-to-image-2"),
    Item("9", "Avion", "0,23 kg CO2e / km. Les voyages en avion ont une empreinte carbone élevée en raison de la combustion de carburant et des émissions de gaz à effet de serre associées.", +300, "url-to-image-2"),
    Item("10", "Industrie pétrolière", "Pas de valeur précise mais impact catastrophique. L'extraction et le raffinage du pétrole ont une empreinte carbone élevée en raison de la combustion du pétrole et du gaz naturel.", +400, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701998711/jr4iixok7sjmzqpe8jbf.png"),
    Item("11", "Fast-food", "7,26 kg CO2e / repas. Les chaînes de restauration rapide ont souvent une empreinte carbone élevée en raison de l'utilisation de viande et d'emballages à usage unique.", +40, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701998789/ysjzxvgduclmmheihmpj.png"),
    Item("12", "Récupération et recyclage", "Chaque étude montre que le recyclage et la réutilisation ont des effets positifs sur la réduction des gaz à effet de serre, principalement par la récupération de l'énergie, de l'eau et des matériaux utilisés pour fabriquer ces produits. La réutilisation, le recyclage et la réduction des déchets contribuent à réduire les émissions liées à la production de nouveaux matériaux.", -40, "url-to-image-2"),
    Item("13", "Énergie solaire et éolienne", "Emet entre 14 et 15 g éq. CO₂ par kWh d'électricité produite. L'utilisation de l'énergie solaire et éolienne pour la production d'électricité contribue à réduire les émissions de gaz à effet de serre par rapport aux combustibles fossiles.", -20, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701998789/ysjzxvgduclmmheihmpj.png"),
    Item("14", "Bus", "0,11 kg CO2e / km. Les autobus ont un bilan carbone plus favorable que les voitures individuelles en raison de leur capacité à transporter de nombreux passagers à la fois, réduisant ainsi l'empreinte carbone par personne transportée. Ils émettent également moins de gaz à effet de serre par kilomètre parcouru, en particulier s'ils utilisent des carburants propres ou des technologies électriques. Les systèmes de bus efficaces encouragent l'utilisation des transports en commun et contribuent à la réduction des émissions liées à la circulation automobile.", -10, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701998197/tzzjfoix4jpxk8zu9i9k.png"),
    Item("15", "Cigarette", "0,01 kg CO2e / unité. La cigarette a un impact environnemental négatif en contribuant à la déforestation, à l'utilisation d'engrais chimiques, à la pollution de l'eau, aux émissions de gaz à effet de serre dans la production et la combustion, à la génération de déchets toxiques (mégots), et aux risques d'incendies de forêt. Bien que son impact individuel soit limité, l'ensemble du cycle de vie du tabac a un effet cumulatif sur le climat et l'environnement, soulignant l'importance de réduire la consommation de tabac pour des raisons de santé et environnementales.", +20, "url-to-image-2"),
    Item("16", "Carotte", "0,36 kg CO2e / kg. Pas très émetteur ,  les carottes ont un bilan carbone relativement faible par rapport à de nombreux autres aliments, en particulier si elles sont cultivées localement et de manière durable. Le fait de privilégier les carottes produites localement et de saison, ainsi que de réduire les déchets alimentaires, peut contribuer à réduire l'impact environnemental des carottes que vous consommez.", -20, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701998004/psn9u0duefaru0ndow5b.png"),
    Item("17", "Champignon", "0,49 kg CO2e / kg. Les champignons ont effectivement une empreinte carbone relativement faible. Ils nécessitent moins d'espace et de ressources pour leur culture par rapport à de nombreuses autres cultures alimentaires. De plus, les champignons peuvent être cultivés sur des substrats qui seraient autrement considérés comme des déchets, tels que le marc de café ou les copeaux de bois, ce qui en fait une option durable et respectueuse de l'environnement.", -20, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701997952/xka8dgbztabwz8o9dszf.png"),
    Item("18", "Transport en vélo ou à pied", "0 kg co2 / kg. Émissions nulles comparées aux véhicules motorisés.", -50, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701997886/alhpuzxhdbawqabmkwuw.png"),
    Item("19", "Bouteilles en plastique", "0,27 kg CO2e / litre. Leur production et leur élimination sont très polluantes.", +30, "https://res.cloudinary.com/dfkkgyk5w/image/upload/v1701997826/bjmic2xihxyzitengppm.png"),
    Item("20", "Mangue", "10,64 kg CO2e / kg.", +10, "url-to-image-2"),
    // Ajoutez plus d'items initiaux ici si nécessaire
  )

  // Méthode pour lister tous les items
  def list(): Future[List[Item]] = Future {
    items
  }

  // Méthode pour obtenir un item par ID
  def get(id: String): Future[Option[Item]] = Future {
    logger.trace(s"get: id = $id")
    items.find(_.id == id)
  }

  def getByName(name: String): Future[Option[Item]] = Future {
    logger.trace(s"get: name = $name")
    items.find(_.name == name)
  }

  // Méthode pour créer un nouvel item
  def create(item: Item): Future[Unit] = Future {
    items = items :+ item
  }

  // Ajoutez d'autres méthodes si nécessaire, comme la mise à jour ou la suppression d'items
}
