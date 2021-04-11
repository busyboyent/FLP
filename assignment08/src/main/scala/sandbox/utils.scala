package sandbox

object utils {
  implicit class ExtSyntax[F[_], A, B](f: A => F[B]) {
    def >=>[C](g: B => F[C])(implicit mf: Monad[F]): A => F[C] = a => mf.flatMap(f(a))(g)
  }

  def pure[F[_], A](a: A)(implicit mf: Monad[F]): F[A] = mf.pure(a)
}
