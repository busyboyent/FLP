package RecommendationService

import RecommendationService.Repositories.Books.BookInfo
import RecommendationService.Repositories.Films.FilmInfo

sealed trait Activity

case class ReadBook(bookInfo: BookInfo) extends Activity
case class SeeFilm(filmInfo: FilmInfo)  extends Activity
