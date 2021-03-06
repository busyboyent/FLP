Рекомендательный сервис
=======================

В этой задаче мы попробуем реализовать простой сервис
рекомендаций на основе уже имеющихся данных

Вам надо реализовать `RecommendationService` так, чтобы:

* Методы `whatToRead`, `whatToSee` возвращали
результат в течении 10 секунд, иначе ошибка.
* Эти методы (`whatToRead`, `whatToSee`) должны
опираться на то, что возвращает `UserInfoRepo`. Например, при реализации метода
`whatToRead`
метод `getReadBooks` вернёт список `id` книг, которые читал пользователь.
Мы проверим все книги, используя метод `getBookById` из `BookRepo`.
Выберем жанр, который в полученном списке книг встречается чаще всего.
Запросим список книг по жанру(`getBooksByGenre`) и вернём такую книгу у которой самый высокий рейтинг
и которую еще не читал наш пользователь. Если такой книги нет, то 
кинем ошибку, что книга не найдена. Текст ошибки: "Book not found".
Для метода `whatToSee` соответственно "Film not found".
Методы не должны падать, если по какому-либо из `id` информация не была найдена.

* `whatToDo` собирает всё информацию по прочитанным книгам и 
просмотренным фильмам, выбирает наиболее популярный у пользователя жанр. Делает
запросы по жанру, собирая фильмы и книги. Выбирает сущность с самым высоким
рейтингом и отдает её. Если у двух сущностей рейтинги равны, то приоритет 
определяется так: книга, фильм.

* `whatToDo` должен продолжать работать до тех пор, пока он получает информацию
хотя бы по одной из сущностей: книги, фильмы. 

* Если `whatToDo` ничего подходящего не нашёл, то вернуть ошибку с текстом: 
"Not founded activity :("

* `whatToDo` тоже не падает, если по id сущности нельзя получить информацию

* Гарантируется что у двух книг/фильмов не будет одинакового рейтинга.
Но у фильма и книги может быть одинаковый рейтинг

* Рейтинг считается начиная с 0

* Гарантируется что у всего есть хотя бы один жанр

* Если оказывается, что у пользователя несколько популярных жанров, т.е. жанры
встречаются с одинаковой частотой, то выбираем первый в алфавитном порядке

* Старайтесь делать код как можно более производительным. Следите, если ваши фьючи
могут работать независимо друг от друга, то они должны работать независимо друг от друга
