package RecommendationService.Repositories.Books
import scala.concurrent.Future

object BookRepoStub extends BookRepo {

  override def getBookById(id: BookId): Future[BookInfo] =
    Future.successful(
      BookInfo(BookId("42"), "Алые паруса", Set("Classic", "Drama"), 86)
    )

  override def getBooksByGenre(genre: String): Future[List[BookInfo]] = Future.successful(List(
    BookInfo(BookId("42"), "Алые паруса", Set("Classic", "Drama"), 86)
  ))
}
