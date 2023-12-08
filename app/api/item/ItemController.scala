package api.item
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json

// Importez vos modèles et dépôts ici
import model.Item

// Un formulaire pour valider les données de l'item
case class ItemFormInput(name: String, description: String, score: Int, imageUrl: String)

object ItemFormInput {
  import play.api.data.Forms._
  import play.api.data.Form

  // Définissez le formulaire ici
  val form: Form[ItemFormInput] = Form(
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "score" -> number,
      "imageUrl" -> nonEmptyText
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

  def showByName(name: String): Action[AnyContent] = Action.async { implicit request =>
    itemRepository.getByName(name).map {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  def create: Action[AnyContent] = Action.async { implicit request =>
    ItemFormInput.form.bindFromRequest().fold( // Ajoutez les parenthèses vides ici
      errorForm => {
        Future.successful(BadRequest("Invalid data"))
      },
      data => {
        // Assurez-vous que tous les champs nécessaires sont inclus ici
        val newItem = Item(
          id = java.util.UUID.randomUUID().toString, // Générez un ID unique si nécessaire
          name = data.name,
          description = data.description,
          score = data.score,
          imageUrl = data.imageUrl // Assurez-vous que ce champ est fourni
        )
        itemRepository.create(newItem).map { _ =>
          Created(Json.toJson(newItem))
        }
      }
    )
  }



  // Ajoutez d'autres actions si nécessaire, comme la mise à jour ou la suppression d'items
}

//Item(id: String, name: String, description: String, score: Int, imageUrl: String)
