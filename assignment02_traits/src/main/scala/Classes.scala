import Ideology.HonestIdeology
import Races.{Elf, Human, Orc, _}

object Classes {

  trait TricksterClasses
  trait WarriorClasses

  trait Warrior extends Character with HonestIdeology {
    self: WarriorClasses => override def getCharacter: String = super.getCharacter ++ ", warrior"
  }

  trait Wizard extends Character with HonestIdeology {
    override def getCharacter: String = super.getCharacter ++ ", wizard"
  }

  trait Trickster extends Character {
    self: TricksterClasses => override def getCharacter: String = super.getCharacter ++ ", trickster"
  }

  trait Warlock extends Character {
    override def getCharacter: String = super.getCharacter ++ ", warlock"
  }


}