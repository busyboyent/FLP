import Classes._
import Ideology._
import Races._

object BaseSkills {

  trait Eloquence extends Character {
    override def getCharacter: String = super.getCharacter ++ ", base skill: eloquence"
  }

  trait Charisma extends Character {
    override def getCharacter: String = super.getCharacter ++ ", base skill: charisma"
  }

  trait VeryStrong extends Character {
    self: Orc with Warrior => override def getCharacter: String = super.getCharacter ++ ", base skill: very strong"
  }

  trait Wisdom extends Character {
    override def getCharacter: String = super.getCharacter ++ ", base skill: wisdom"
  }

  trait Inconspicuous extends Character {
    self: Halfling with Trickster => override def getCharacter: String = super.getCharacter ++ ", base skill: inconspicuous"
  }
}