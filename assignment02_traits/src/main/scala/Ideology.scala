import Classes._

object Ideology {

  trait HonestIdeology

  trait Wicked extends Character {
    self: Warlock => override def getCharacter: String = super.getCharacter ++ ", wicked"
  }

  trait Neutral extends Character {
    override def getCharacter: String = super.getCharacter ++ ", neutral"
  }

  trait Honest extends Character {
    self: HonestIdeology => override def getCharacter: String = super.getCharacter ++ ", honest"
  }
}