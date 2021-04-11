package RecommendationService.Repositories.UserInfo

import RecommendationService.Repositories.Books.BookId
import RecommendationService.Repositories.Films.FilmId

import scala.concurrent.Future

trait UserInfoRepo {
  def getReadBooks(userId: UserId): Future[List[BookId]]
  def getWatchedFilms(userId: UserId): Future[List[FilmId]]
}
