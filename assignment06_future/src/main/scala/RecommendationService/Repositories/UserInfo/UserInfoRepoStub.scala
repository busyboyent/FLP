package RecommendationService.Repositories.UserInfo
import RecommendationService.Repositories.Books.BookId
import RecommendationService.Repositories.Films.FilmId

import scala.concurrent.Future

object UserInfoRepoStub extends UserInfoRepo {

  override def getReadBooks(userId: UserId): Future[List[BookId]] = Future.successful(List(BookId("13")))

  override def getWatchedFilms(userId: UserId): Future[List[FilmId]] = Future.successful(List(FilmId("32")))
}
