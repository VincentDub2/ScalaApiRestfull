package api.item
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json

import model.Item

// Un formulaire pour valider les données de l'item
case class ItemFormInput(name: String, description: String, score: Int, imageUrl: String, CO2: String)

object ItemFormInput {
  import play.api.data.Forms._
  import play.api.data.Form

  // Définissez le formulaire ici
  val form: Form[ItemFormInput] = Form(
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "score" -> number,
      "imageUrl" -> nonEmptyText,
      "CO2" -> nonEmptyText
    )(ItemFormInput.apply)(ItemFormInput.unapply)
  )
}

// Le contrôleur pour les items
class ItemController @Inject()(cc: ControllerComponents, itemRepository: ItemRepository)
                              (implicit ec: ExecutionContext) extends AbstractController(cc) {

  private val logger = Logger(getClass)

  // Action pour afficher la liste des items
  def index: Action[AnyContent] = Action.async { implicit request =>
    logger.trace("index: ")
    itemRepository.list().map { items =>
      Ok(Json.toJson(items))
    }
  }

  // Action pour afficher un item spécifique par son ID
  def show(id: String): Action[AnyContent] = Action.async { implicit request =>
    itemRepository.get(id).map {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  // Action pour afficher un item spécifique par son nom
  def showByName(name: String): Action[AnyContent] = Action.async { implicit request =>
    itemRepository.getByName(name).map {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  // Action pour créer un nouvel item
  // Non utilisé dans ici
  def create: Action[AnyContent] = Action.async { implicit request =>
    ItemFormInput.form.bindFromRequest().fold(
      errorForm => {
        Future.successful(BadRequest("Invalid data"))
      },
      data => {
        val newItem = Item(
          id = java.util.UUID.randomUUID().toString, // Génére un ID unique
          name = data.name,
          description = data.description,
          score = data.score,
          imageUrl = data.imageUrl,
          CO2 = data.CO2
        )
        itemRepository.create(newItem).map { _ =>
          Created(Json.toJson(newItem))
        }
      }
    )
  }
}


