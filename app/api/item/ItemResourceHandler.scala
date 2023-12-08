package api.item
import model.Item

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.{Inject, Singleton}
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

  // Ajoutez d'autres méthodes si nécessaire, comme la mise à jour ou la suppression d'items
}
