package RecommendationService

import RecommendationService.Repositories.Books.BookInfo
import RecommendationService.Repositories.Films.FilmInfo
import RecommendationService.Repositories.UserInfo.UserId

import scala.concurrent.Future

trait RecommendationService {
  def whatToRead(userId: UserId): Future[BookInfo]

  def whatToSee(userId: UserId): Future[FilmInfo]

  def whatToDo(userId: UserId): Future[Activity]
}
