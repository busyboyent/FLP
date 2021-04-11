package RecommendationService.Repositories.Films
import scala.concurrent.Future

object FilmRepoStub extends FilmRepo {

  override def getFilmById(id: FilmId): Future[FilmInfo] = Future.successful(
    FilmInfo(FilmId("filmId"), "Город Зеро", Set("Symbolism", "Drama"), 89)
  )

  override def getFilmsByGenre(genre: String): Future[List[FilmInfo]] = Future.successful(List(
    FilmInfo(FilmId("filmId"), "Город Зеро", Set("Symbolism", "Drama"), 89)
  ))
}
