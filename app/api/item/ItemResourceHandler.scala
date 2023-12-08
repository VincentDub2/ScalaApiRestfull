package api.item
import model.Item

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.{Inject, Singleton}

// ItemResourceHandler est un composant qui gère les opérations CRUD pour les items
class ItemResourceHandler @Inject()(itemRepository: ItemRepository)(implicit ec: ExecutionContext) {

  // Méthode pour créer un nouvel item
  def create(item: Item): Future[Item] = {
    itemRepository.create(item).map(_ => item)
  }

  // Méthode pour récupérer un item par son ID
  def lookup(id: String): Future[Option[Item]] = {
    itemRepository.get(id)
  }

  // Méthode pour récupérer tous les items
  def find(): Future[Iterable[Item]] = {
    itemRepository.list()
  }
}
