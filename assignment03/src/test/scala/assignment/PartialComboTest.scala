package assignment

import org.scalatest.Matchers
import org.scalatest.flatspec.AnyFlatSpec

class PartialComboTest extends AnyFlatSpec with Matchers {

  val partialPositive: PartialFunction[Int, String] = {
    case n if n > 0 => "Positive"
  }

  val partialNegative: PartialFunction[Int, String] = {
    case n if n > 0 => "Negative"
  }

  val partialFunctions = List(partialPositive, partialNegative)

  "PartialCombo.combo" should "combine partial functions" in {
    PartialCombo.combo(partialFunctions)(1) should be (Some("Positive"))
  }
}
