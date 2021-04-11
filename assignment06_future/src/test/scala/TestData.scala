import RecommendationService.Repositories.Books.{BookId, BookInfo}
import RecommendationService.Repositories.Films.{FilmId, FilmInfo}

object TestData {
  object Books {
    val book1 = BookInfo(BookId("1"), "Посторонний", Set("classic", "detective", "psychology", "drama"), 32)
    val book2 = BookInfo(BookId("2"), "Мертвый город", Set("classic", "play", "drama"), 34)

    val allBooks = List(book1, book2)
  }

  object Films {
    val film1 = FilmInfo(FilmId("1"), "Психо", Set("classic", "detective", "psychology", "drama"), 32)
    val film2 = FilmInfo(FilmId("2"), "Царь Эдип", Set("classic", "play", "drama"), 34)

    val allFilms = List(film1, film2)
  }

}
