package assignment

import scala.annotation.tailrec

object PartialCombo {

  def combo[I,T](funcs: List[PartialFunction[I,T]]): I => Option[T] = {
    @scala.annotation.tailrec
    def loop(acc: PartialFunction[I,T], func: List[PartialFunction[I,T]]): PartialFunction[I,T] = func match {
      case Nil => acc
      case x :: xs => loop((acc.orElse(x)), xs)
    }

    result => funcs match {
      case Nil => None
      case x :: xs => loop(x, xs).lift(result)
    }
  }
}
