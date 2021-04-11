import RecommendationService.{RecommendationService, RecommendationServiceImpl}
import _root_.RecommendationService.Repositories.Books.BookRepoStub
import _root_.RecommendationService.Repositories.Films.FilmRepoStub
import _root_.RecommendationService.Repositories.UserInfo.{UserId, UserInfoRepoStub}

object Main extends App {
  val rs: RecommendationService = new RecommendationServiceImpl(UserInfoRepoStub, FilmRepoStub, BookRepoStub)
  val wr = rs.whatToRead(UserId("13"))
  val ws = rs.whatToSee(UserId("13"))
  val wd = rs.whatToDo(UserId("13"))

  Thread.sleep(2000)
  println(wr)
  println(ws)
  println(wd)
}
