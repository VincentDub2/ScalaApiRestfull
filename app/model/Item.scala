package model

// Définition du modèle Item
case class Item(id: String, name: String, description: String, score: Int, imageUrl: String)

object Item {
  import play.api.libs.json._

  // Définition implicite pour la conversion JSON
  implicit val itemFormat: Format[Item] = Json.format[Item]
}
