import BaseSkills.{Charisma, Eloquence, Inconspicuous, VeryStrong, Wisdom}
import Classes.{Trickster, Warlock, Warrior, Wizard}
import Ideology.{Honest, Neutral, Wicked}
import Races.{Elf, Halfling, Human, Orc}
import org.scalatest._
import org.scalatest.matchers.should.Matchers


class RPGSystemTest extends FlatSpec with Matchers {

  "RPGSystem" should "correct to create character 01" in {
    val hw   = new Human with Warrior {}
    val hwh  = new Human with Warrior with Honest {}
    val hwhe = new Human with Honest with Warrior with Eloquence {}
    val wh   = new Warrior with Human {}
    val hehw = new Human with Eloquence with Honest with Warrior {}

    hw.getCharacter   shouldBe "Human, warrior"
    hwh.getCharacter  shouldBe "Human, warrior, honest"
    hwhe.getCharacter shouldBe "Human, honest, warrior, base skill: eloquence"
    wh.getCharacter   shouldBe "Human"
    hehw.getCharacter shouldBe "Human, base skill: eloquence, honest, warrior"
  }

  it should "prevent compilation for Orc-trickster" in {
    "new Orc with Trickster {}" shouldNot typeCheck
    "new Orc with Wizard {}" should compile
  }

  it should "prevent compilation for Halfling-warrior" in {
    "new Halfling with Warrior {}" shouldNot typeCheck
    "new Halfling with Warlock {}" should compile
  }

}
