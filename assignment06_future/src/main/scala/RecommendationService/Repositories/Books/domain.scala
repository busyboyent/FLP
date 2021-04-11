package RecommendationService.Repositories.Books

case class BookInfo(id: BookId, name: String, genres: Set[String], rate: Int)

case class BookId(id: String) extends AnyVal
