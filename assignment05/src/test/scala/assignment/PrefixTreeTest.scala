package assignment

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PrefixTreeTest extends AnyFlatSpec with Matchers {

  "PrefixTree" should "behave like prefix tree" in {
    val empty = PrefixTree.empty[String, Int]

    val tree1 = empty.put(List("one", "two", "tree"), 123)
    val tree2 = tree1.put(List("one", "two"), 12)

    assert(tree1.size == 1)
    assert(tree2.size == 2)

    assert(tree1.get(List("one", "two", "tree")) == Some(123))
    assert(tree2.get(List("one", "two", "tree")) == Some(123))
    assert(tree1.get(List("one", "two")) == None)
    assert(tree2.get(List("one", "two")) == Some(12))
  }

}
