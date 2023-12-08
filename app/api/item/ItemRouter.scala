package api.item

import play.api.i18n.Lang.logger
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._


import javax.inject.Inject

/**
 * Routes and URLs to the ItemController.
 */
class ItemRouter @Inject()(controller: ItemController) extends SimpleRouter {
  val prefix = "/api/item"

  // Méthode pour générer un lien vers un item spécifique
  def link(id: String): String = {
    val url = prefix + "/" + id
    url
  }

  override def routes: Routes = {
    case GET(p"/") =>
      controller.index  // Récupérer tous les items

    case GET(p"/name/$name") =>
      controller.showByName(name)  // Récupérer un item spécifique par son nom

    case GET(p"/id/$id") =>
      controller.show(id)  // Récupérer un item spécifique par son ID

    case POST(p"/") =>
      controller.create  // Créer un nouvel item

    // Ajoutez d'autres routes ici si nécessaire, par exemple pour la mise à jour ou la suppression
  }
}
