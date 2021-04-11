package sandbox
import Monad.MonadSyntax
case class WriterT[F[_], L, T](value: F[T], writer: F[List[L]])

object WriterT {
  implicit def writerMonad[F[_], L](implicit mf: Monad[F]): Monad[WriterT[F, L, *]] = new Monad[WriterT[F, L, *]] {
    override def pure[A](a: => A): WriterT[F, L, A] = WriterT(mf.pure(a), mf.pure(List.empty))

    override def flatMap[A, B](fa: WriterT[F, L, A])(f: A => WriterT[F, L, B]): WriterT[F, L, B] = {
      val tupl = for {
        startWriter <- fa.writer
        startVal <- fa.value
        fb = f(startVal)
        newWriter <- fb.writer
        endVal <- fb.value
        endWriter = startWriter ++ newWriter
      } yield (endVal, endWriter)
      WriterT(tupl.map(el => el._1), tupl.map(el => el._2))
    }
  }
}
