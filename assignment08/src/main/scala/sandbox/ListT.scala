package sandbox
import Monad.MonadSyntax
case class ListT[F[_], A](value: F[List[A]])

object ListT {
  implicit def ListTMonad[F[_]](implicit mf: Monad[F]): Monad[ListT[F, *]] = new Monad[ListT[F, *]] {
    override def pure[A](a: => A): ListT[F, A] = ListT(mf.pure(List(a)))

    override def flatMap[A, B](fa: ListT[F, A])(f: A => ListT[F, B]): ListT[F, B] = {
      val default = mf.pure(List[B]())
      ListT(fa.value.flatMap(_.map(f(_).value).foldLeft(default)(sumLst)))
    }

    def sumLst[B](lst1: F[List[B]], lst2: F[List[B]]): F[List[B]] = lst1.flatMap(x => lst2.map(x ++ _))
  }
}
