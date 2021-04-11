package sandbox

case class StateT[F[_], S, A](run: S => F[(A, S)])

object StateT {
  implicit def stateTMonad[F[_], S](implicit mf: Monad[F]): Monad[StateT[F, S, *]] = new Monad[StateT[F, S, *]] {
    override def pure[A](a: => A): StateT[F, S, A] = StateT( s => mf.pure(a, s))

    override def flatMap[A, B](fa: StateT[F, S, A])(f: A => StateT[F, S, B]): StateT[F, S, B] = StateT { s =>
      mf.flatMap(fa.run(s))(a => f(a._1).run(a._2))
    }
  }
}
