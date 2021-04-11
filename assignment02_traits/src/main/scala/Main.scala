import Races._
import Classes._
import BaseSkills._
import Ideology._

object Main extends App {

  val a = new Human with Warlock with Eloquence with Wicked {}
  println(a.getCharacter)

}
