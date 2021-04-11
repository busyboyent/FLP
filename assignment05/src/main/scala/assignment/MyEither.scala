package assignment

/**
 * Реализуйте интерфейс MyEither.
 * MyEither.error возвращает объект содержащий ошибку
 * MyEither.value возращает объект содержащий значение (успешный результат)
 */
sealed trait MyEither[+E, +A] {

  def map[B](f: A => B): MyEither[E, B] = this match {
    case Right(value) => Right(f(value))
    case Wrong(value) => Wrong(value)
  }

  def flatMap[EE >: E, B](f: A => MyEither[EE, B]): MyEither[EE, B] = this match {
    case Right(value) => f(value)
    case Wrong(value) => Wrong(value)
  }

  def orElse[EE >: E, B >: A](b: => MyEither[EE, B]): MyEither[EE, B] = this match {
    case Wrong(_) => b
    case Right(value) => Right(value)
  }

  def map2[EE >: E, B, C](b: => MyEither[EE, B])(f: (A, B) => C): MyEither[EE, C] = this match {
    case Right(first) => b match {
      case Right(second) => Right(f(first, second))
      case Wrong(second) => Wrong(second)
    }
    case Wrong(first) => Wrong(first)
  }

}


object MyEither {
  def error[E](e: E): MyEither[E, Nothing] = {
    Wrong(e)
  }
  def value[A](a: A): MyEither[Nothing, A] = {
    Right(a)
  }
}

case class Wrong[E](value: E) extends MyEither[E, Nothing]
case class Right[A](value: A) extends MyEither[Nothing, A]