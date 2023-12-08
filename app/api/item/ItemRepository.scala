package api.item
import model.Item
import play.api.i18n.Lang.logger

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

// Vous devez importer votre système de gestion de base de données et le contexte d'exécution ici

class ItemRepository @Inject()(implicit ec: ExecutionContext) {
  // Simuler une base de données en mémoire
  private var items: List[Item] = List(
    Item("1", "Item 1", "Description 1", 10, "url-to-image-1"),
    Item("2", "Item 2", "Description 2", 20, "url-to-image-2")
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

  // Méthode pour créer un nouvel item
  def create(item: Item): Future[Unit] = Future {
    items = items :+ item
  }

  // Ajoutez d'autres méthodes si nécessaire, comme la mise à jour ou la suppression d'items
}
