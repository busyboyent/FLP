package assignment

/**
 * Реализовать интерфейс PrefixTree
 * Интерфейс позволяет складывать объекты произвольного
 * класса V по заданному "пути" List[K] в дерево
 * и изымать их используя комбинацию методов sub и get
 */

// Например, можно на каждом "уровне" дерева хранить Option[V] и Map[K, PrefixTree[K, V]]
trait PrefixTree[K, +V] {

  def get(path: List[K]): Option[V]

  def sub(path: List[K]): PrefixTree[K, V]

  def put[U >: V](path: List[K], value: U): PrefixTree[K, U]

  def size: Int
}

object PrefixTree {
  def empty[K, V]: Tree[K, V] = Tree(None, Map.empty)
}

case class Tree[K, +V](value: Option[V], trees: Map[K, Tree[K,V]]) extends PrefixTree[K, V]{
  override def get(path: List[K]): Option[V] = path match {
    case Nil => value
    case head :: tail => trees.get(head).flatMap(_.get(tail))
  }

  def sub(path: List[K]): Tree[K, V] = path match {
    case Nil => Tree(value, trees)
    case head :: tail => trees.get(head) match {
      case None => Tree(None, Map.empty)
      case Some(subtree) => subtree.sub(tail)
    }
  }

  def put[U >: V](path: List[K], value: U): Tree[K, U] = path match {
    case Nil => Tree[K, U](Some(value), trees)
    case ::(head, tl) => trees.get(head) match {
      case None => Tree[K, U](this.value, trees + (head -> PrefixTree.empty.put(tl, value)))
      case Some(subtree) =>
        val leaf = subtree.put(tl, value)
        Tree[K, U](this.value, trees + (head -> leaf))
    }
  }

  def size: Int = {
    val start = if(this.value.isDefined) 1 else 0
    val subTrees = this.trees
    subTrees.foldLeft(start){case (s, (_, nextTree)) => s + nextTree.size}
  }
}
