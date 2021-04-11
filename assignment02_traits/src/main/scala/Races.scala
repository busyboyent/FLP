import Character._
import Classes._

object Races {

  trait Human extends Character with WarriorClasses with TricksterClasses {
    override def getCharacter: String = "Human"
  }

  trait Orc extends Character with WarriorClasses {
    override def getCharacter: String = "Orc"
  }

  trait Elf extends Character with WarriorClasses with TricksterClasses {
    override def getCharacter: String = "Elf"
  }

  trait Halfling extends Character with TricksterClasses {
    override def getCharacter: String = "Halfling"
  }
}
