package assignment

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HandmanTest extends AnyFlatSpec with Matchers {
  // "> " помечают ввод от пользователя



  "Hangman" should "play winning game" in {
    val winData =
      """Word: ___
Guess a letter:
> K
Word: K_K
Guess a letter:
> E
Word: KEK
GG
"""

    val io = TestIOConsole.create(winData)
      .flatMap(c => new Hangman(c).play("KEK"))

    io.unsafeRunSync()
  }

  it should "play loosing game" in {
    val lostData =
      """Word: ___
Guess a letter:
> A
+----
|
|
|
|
|

Word: ___
Guess a letter:
> B
+----
|
|   O
|
|
|

Word: ___
Guess a letter:
> C
+----
|
|   O
|   |
|
|

Word: ___
Guess a letter:
> D
+----
|
|   O
|   |
|  /
|

Word: ___
Guess a letter:
> E
+----
|
|   O
|   |
|  /
|

Word: _E_
Guess a letter:
> F
+----
|
|   O
|   |
|  / \
|

Word: _E_
Guess a letter:
> G
+----
|
|   O
|  /|
|  / \
|

Word: _E_
Guess a letter:
> H
+----
|
|   O
|  /|\
|  / \
|

Word: _E_
Guess a letter:
> I
+----
|   |
|   O
|  /|\
|  / \
|

You are dead
"""

    val io = TestIOConsole.create(lostData)
      .flatMap(c => new Hangman(c).play("KEK"))

    io.unsafeRunSync()
  }

}
