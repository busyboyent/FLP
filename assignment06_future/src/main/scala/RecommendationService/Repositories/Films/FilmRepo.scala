package RecommendationService.Repositories.Films

import scala.concurrent.Future

trait FilmRepo {
  def getFilmById(id: FilmId): Future[FilmInfo]
  def getFilmsByGenre(genre: String): Future[List[FilmInfo]]
}
