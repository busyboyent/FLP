package RecommendationService

import java.util.{Timer, TimerTask}

import RecommendationService.Repositories.Books.{BookInfo, BookRepo}
import RecommendationService.Repositories.Films.{FilmInfo, FilmRepo}
import RecommendationService.Repositories.UserInfo.{UserId, UserInfoRepo}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

class RecommendationServiceImpl(userInfoRepo: UserInfoRepo,
                                filmRepo: FilmRepo,
                                bookRepo: BookRepo) extends RecommendationService {
  val timer = new Timer(true)

  def timeout[A](future: Future[A]): Future[A] = {
    val p = Promise[A]
    val tt = new TimerTask {
      override def run(): Unit = p.tryFailure(new Exception("Timeout exception"))
    }
    timer.schedule(tt, 10000)
    Future.firstCompletedOf(List(future, p.future))
  }

  def getPopular[A](genres: List[String]): String = {
    genres.groupBy(identity).toList.sortBy(name => name._1).maxBy(amount => amount._2.size)._1
  }

  def getInformation[A](entities: List[Future[A]]): Future[List[A]] = {
    Future.traverse(entities)(entity => entity.map(info => Some(info))
      .recover {case _ => None})
      .map(x => x.collect{case Some(x) => x})
  }

  override def whatToRead(userId: UserId): Future[BookInfo] = {
    val book = for {
      readBookID <- userInfoRepo.getReadBooks(userId)
      readBookInfo <- getInformation(readBookID.map(book => bookRepo.getBookById(book)))
      mostPopularGenre = getPopular(readBookInfo.flatMap(book => book.genres))
      bookPopularGenre <- bookRepo.getBooksByGenre(mostPopularGenre)
      result = bookPopularGenre.filter(book => !readBookID.contains(book.id)).maxBy(book => book.rate)
    } yield result
    timeout(book.transform(identity, _ => new Exception("Book not found")))
  }

  override def whatToSee(userId: UserId): Future[FilmInfo] = {
    val film = for {
      watchFilm <- userInfoRepo.getWatchedFilms(userId)
      watchFilmInfo <- getInformation(watchFilm.map(film => filmRepo.getFilmById(film)))
      mostPopularGenre = getPopular(watchFilmInfo.flatMap(film => film.genres))
      filmPopularGenre <- filmRepo.getFilmsByGenre(mostPopularGenre)
      result = filmPopularGenre.filter(film => !watchFilm.contains(film.id)).maxBy(film => film.rate)
    } yield result
    timeout(film.transform(identity, _ => new Exception("Film not found")))
  }

  override def whatToDo(userId: UserId): Future[Activity] = {
    val result = for {
      readBook <- userInfoRepo.getReadBooks(userId).transform(identity)
      readBookInfo <- getInformation(readBook.map(book => bookRepo.getBookById(book)))
      watchFilm <- userInfoRepo.getWatchedFilms(userId)
      watchfilmInfo <- getInformation(watchFilm.map(film => filmRepo.getFilmById(film)))
      mostPopularGenre = getPopular(readBookInfo.flatMap(book => book.genres) ++ watchfilmInfo.flatMap(film => film.genres))
      bookPopularGenre <- bookRepo.getBooksByGenre(mostPopularGenre)
      filmPopularGenre <- filmRepo.getFilmsByGenre(mostPopularGenre)
      bestBook = bookPopularGenre.filter(book => !readBook.contains(book.id))
      bestFilm = filmPopularGenre.filter(film => !watchFilm.contains(film.id))
      best = (bestBook, bestFilm) match {
        case (Nil, _) => SeeFilm(bestFilm.maxBy(film => film.rate))
        case (_, Nil) => ReadBook(bestBook.maxBy(book => book.rate))
        case (x, y) if x.maxBy(book => book.rate).rate >= y.maxBy(film => film.rate).rate => ReadBook(bestBook.maxBy(book => book.rate))
        case (_, _) => SeeFilm(bestFilm.maxBy(film => film.rate))
      }
    } yield best
    result.transform(identity, _ => new Exception("Not found activity :("))
  }
}
