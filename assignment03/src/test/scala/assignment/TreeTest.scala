package assignment

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TreeTest extends AnyFlatSpec with Matchers {

  val tree =
    Branch(
      Branch(
        Leaf(4),
        Branch(
          Leaf(2),
          Leaf(5)
        )
      ),
      Branch(
        Leaf(1),
        Branch(
          Leaf(1),
          Leaf(3)
        )
      )
    )

  "Tree.size" should "count all nodes" in {
    Tree.size(tree) should be (11)
  }

  "Tree.max" should "find max int in tree" in {
    Tree.max(tree) should be (5)
  }

  "Tree.depth" should "calculate maximum tree depth" in {
    Tree.depth(tree) should be (4)
  }


}
