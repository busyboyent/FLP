package exercises

import org.scalacheck.{Prop, Properties}

class HangmanGameSpec extends Properties("HangmanGameSpec") {

  property("some game") = Prop {
    val testIO = new TestIO(testData)
    val game = new Hangman(testIO)

    game.play("KEK")
    testIO.queue.isEmpty
  }

  // "> " помечают ввод от пользователя
  val testData =
    """
Word: ___
Guess a letter:
> K
Word: K_K
Guess a letter:
> E
Word: KEK
GG"""
}