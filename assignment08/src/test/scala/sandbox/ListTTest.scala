package sandbox


import org.scalatest.flatspec.AnyFlatSpec

class ListTTest extends AnyFlatSpec {
  import sandbox.ListT._
  import sandbox.Monad._
  import sandbox.utils._

  "ListT" should "be monad" in {
    val f: Int => ListT[Option, Int] = int => ListT(Some(int + int :: Nil))

    val g: Int => ListT[Option, String] = int => ListT(Some(int.toString :: Nil))

    val h: String => ListT[Option, Boolean] = str =>
      if (str.length % 2 == 0) ListT(Some(true :: true :: Nil))
      else ListT[Option, Boolean](None)


    val l = pure[ListT[Option, *], Int] _ >=> f
    val r = f >=> pure[ListT[Option, *], Int]

    val comp1 = (f >=> g) >=> h
    val comp2 = f >=> (g >=> h)

    assert(l(666) == r(666))
    assert(comp1(42) == comp2(42))
    assert(comp1(1) == ListT[Option, Boolean](None) && comp2(1) == ListT[Option, Boolean](None))
  }
}
