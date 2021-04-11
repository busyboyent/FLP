package RecommendationService.Repositories.Books

import scala.concurrent.Future

trait BookRepo {
  def getBookById(id: BookId): Future[BookInfo]
  def getBooksByGenre(genre: String): Future[List[BookInfo]]
}
