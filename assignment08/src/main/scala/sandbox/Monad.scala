package sandbox

trait Monad[F[_]] {
  def pure[A](a: => A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))
}

object Monad {
  def apply[F[_]](implicit mf: Monad[F]): Monad[F] = mf

  implicit class MonadSyntax[F[_] : Monad, A](fa: F[A]) {
    def flatMap[B](f: A => F[B]) = Monad[F].flatMap(fa)(f)
    def map[B](f: A => B) = Monad[F].map(fa)(f)
  }

  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    override def pure[A](a: => A): Option[A] = Some(a)
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa match {
      case None => None
      case Some(x) => f(x)
    }
  }

}
