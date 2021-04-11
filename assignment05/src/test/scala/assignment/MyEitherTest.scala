package assignment

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MyEitherTest extends AnyFlatSpec with Matchers {

  def divByTwo(i: Int): MyEither[String, Int] =
    if (i % 2 == 0) MyEither.value(i / 2) else MyEither.error("Odd")

  "MyEither.value" should "should be 'success' path" in {
    val e: MyEither[String, Int] = MyEither.value(21)

    assert(e.map(x => (x * 2).toString) == MyEither.value("42"))
    assert(e.flatMap(divByTwo) == MyEither.error("Odd"))
    assert(e.orElse(MyEither.error("Another failure")) == MyEither.value(21))
    assert(e.map2(MyEither.value(37))(_ + _) == MyEither.value(58))
  }

  "MyEither.error" should "should be 'error' path" in {
    val e: MyEither[String, Int] = MyEither.error("Sad")

    assert(e.map(x => (x * 2).toString) == MyEither.error("Sad"))
    assert(e.flatMap(divByTwo) == MyEither.error("Sad"))
    assert(e.orElse(MyEither.error("Another failure")) == MyEither.error("Another failure"))
    assert(e.map2(MyEither.value(37))(_ + _) == MyEither.error("Sad"))
  }
}
