package api.item

import play.api.http.FileMimeTypes
import play.api.i18n.{Langs, MessagesApi}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import play.api.{Logger, MarkerContext}

import javax.inject.Inject



// Un contexte de requête personnalisé pour les items
trait ItemRequestHeader extends MessagesRequestHeader with PreferredMessagesProvider
class ItemRequest[A](request: Request[A], val messagesApi: MessagesApi)
  extends WrappedRequest(request) with ItemRequestHeader

// Fournit un marqueur implicite pour le logging des requêtes
trait RequestMarkerContext {
  import net.logstash.logback.marker.Markers


  // Markers permet de créer des marqueurs pour les logs
  private def marker(tuple: (String, Any)) = Markers.append(tuple._1, tuple._2)

  // Crée un marqueur implicite pour les logs
  // Les marqueurs sont utilisés pour ajouter des informations aux logs
  // Implicit signifie que le compilateur peut trouver ce marqueur sans que nous ayons à l'importer
  implicit def requestHeaderToMarkerContext(implicit request: RequestHeader): MarkerContext = {
    MarkerContext(
      Markers.aggregate(
        marker("id" -> request.id),
        marker("host" -> request.host),
        marker("remoteAddress" -> request.remoteAddress)
      )
    )
  }
}

// Le constructeur d'action pour les ressources d'item
class ItemActionBuilder @Inject()(messagesApi: MessagesApi, playBodyParsers: PlayBodyParsers)
                                 (implicit val executionContext: ExecutionContext)
  extends ActionBuilder[ItemRequest, AnyContent] with RequestMarkerContext {

  override val parser: BodyParser[AnyContent] = playBodyParsers.anyContent


  // Un bloc de requête d'item
  // Il s'agit d'une fonction qui prend une requête d'item et renvoie un résultat
  // Le résultat est un Future, car les requêtes peuvent être asynchrones
  type ItemRequestBlock[A] = ItemRequest[A] => Future[Result]

  private val logger = Logger(this.getClass)

  // Exécute le bloc de requête
  // Override la méthode invokeBlock pour ajouter des en-têtes de cache
  override def invokeBlock[A](request: Request[A], block: ItemRequestBlock[A]): Future[Result] = {
    implicit val markerContext: MarkerContext = requestHeaderToMarkerContext(request)
    logger.trace("invokeBlock: ")

    val future = block(new ItemRequest(request, messagesApi))

    future.map { result =>
      result.withHeaders("Cache-Control" -> "max-age: 100")
    }
  }
}

// Emballe les composants nécessaires pour le contrôleur d'item
case class ItemControllerComponents @Inject()(
                                               itemActionBuilder: ItemActionBuilder,
                                               actionBuilder: DefaultActionBuilder,
                                               parsers: PlayBodyParsers,
                                               messagesApi: MessagesApi,
                                               langs: Langs,
                                               fileMimeTypes: FileMimeTypes,
                                               executionContext: ExecutionContext
                                             ) extends ControllerComponents

// Base pour les contrôleurs qui gèrent les items
abstract class ItemBaseController(pcc: ItemControllerComponents) extends BaseController with RequestMarkerContext {
  override protected def controllerComponents: ControllerComponents = pcc

  def ItemAction: ItemActionBuilder = pcc.itemActionBuilder
}