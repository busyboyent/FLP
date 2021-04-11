package RecommendationService.Repositories.Films

case class FilmInfo(id: FilmId, name: String, genres: Set[String], rate: Int)

case class FilmId(id: String) extends AnyVal

