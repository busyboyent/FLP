package assignment

sealed trait Tree[+A]
final case class Leaf[A](value: A) extends Tree[A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  // реализовать функцию fold
  def fold[A,B](t: Tree[A])(f: A => B)(g: (B,B) => B): B = t match{
    case Leaf(a) => f(a)
    case Branch(l,r) => g(fold(l)(f)(g), fold(r)(f)(g))
  }

  // реализовать следующие функции через fold

  def size[A](t: Tree[A]): Int = {
    fold(t)(leaf => 1)((l, r) => 1 + l + r)
  }

  def max(t: Tree[Int]): Int = {
    fold(t)(n => n)((l, r) => math.max(l, r))
  }

  def depth[A](t: Tree[A]): Int = {
    fold(t)(leaf => 1)((l, r) => 1 + math.max(l, r))
  }

  //здесь возможно придется прибегнуть к насильному указанию типа: Leaf(a): Tree[A]
  def map[A,B](t: Tree[A])(f: A => B): Tree[B] = {
    fold(t)(leaf => Leaf(f(leaf)) : Tree[B])((l, r) => Branch(l, r))
  }
}
