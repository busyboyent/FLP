import RecommendationService.{ReadBook, RecommendationServiceImpl, SeeFilm}
import RecommendationService.Repositories.Books.{BookId, BookInfo, BookRepo}
import RecommendationService.Repositories.Films.{FilmId, FilmRepo}
import RecommendationService.Repositories.UserInfo.{UserId, UserInfoRepo}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalamock.scalatest.{AsyncMockFactory, MockFactory}
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

class RecommendationServiceImplTest extends AsyncFlatSpec with Matchers with AsyncMockFactory {
  import TestData.Books._
  import TestData.Films._

  trait mocks {
    val userInfoMock = mock[UserInfoRepo]
    val filmRepoMock = mock[FilmRepo]
    val bookRepoMock = mock[BookRepo]

    val userId = UserId("userId")

    val service = new RecommendationServiceImpl(userInfoMock, filmRepoMock, bookRepoMock)
  }

  trait bookm extends mocks {
    (bookRepoMock.getBookById _)
      .expects(*)
      .onCall{(id: BookId) => Future(allBooks.find(_.id == id).get)}.anyNumberOfTimes()
  }

  trait filmm extends mocks {
    (filmRepoMock.getFilmById _)
      .expects(*)
      .onCall{(id: FilmId) => Future(allFilms.find(_.id == id).get)}.anyNumberOfTimes()
  }

  "RecommendationServiceImpl" should "return BookInfo when call whatToRead" in {
    val m = new bookm {}
    import m._

    (userInfoMock.getReadBooks _)
      .expects(userId)
      .returns(Future.successful(List(book1.id)))

    (bookRepoMock.getBooksByGenre _)
      .expects("classic")
      .returns(Future.successful(List(book1, book2)))

    service.whatToRead(userId).map {
      _ shouldBe book2
    }
  }

  it should "throw book not found exception if user read all books when call whatToRead" in {
    val m = new bookm {}
    import m._

    (userInfoMock.getReadBooks _).expects(userId).returns(Future.successful(allBooks.map(_.id)))

    service.whatToRead(userId).map {_ => assert(false)}.recover {
      case g => assert(g.getMessage == "Book not found")
    }
  }


  //====================================================================================

  it should "throw film not found exception if user doesn't have read films when call whatToSee" in {
    val m = new filmm {}
    import m._

    (userInfoMock.getWatchedFilms _).expects(userId).returns(Future.successful(List()))

    service.whatToSee(userId).map {_ => assert(false)}.recover {
      case n => assert(n.getMessage == "Film not found")
    }
  }

  it should "pass id of films when not found in repo" in {
    val m = new filmm {}
    import m._

    (userInfoMock.getWatchedFilms _).expects(userId)
      .returns(Future.successful(List(film1.id, FilmId("not found"))))

    (filmRepoMock.getFilmsByGenre _)
      .expects("classic")
      .returns(Future.successful(List(film1, film2)))

    service.whatToSee(userId).map {
      _ shouldBe film2
    }
  }

  //====================================================================================

  it should "return correct answer when call whatToDo" in {
    val m = new filmm with bookm {}
    import m._

    (userInfoMock.getReadBooks _)
      .expects(userId)
      .returns(Future.successful(List(book1.id)))
    (userInfoMock.getWatchedFilms _)
      .expects(userId)
      .returns(Future.successful(List(film1.id, film2.id)))

    (filmRepoMock.getFilmsByGenre _)
      .expects("classic")
      .returns(Future.successful(List(film1, film2)))

    (bookRepoMock.getBooksByGenre _)
      .expects("classic")
      .returns(Future.successful(List(book1, book2)))

    service.whatToDo(userId).map {
      _ shouldBe ReadBook(book2)
    }
  }

}
