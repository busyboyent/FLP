package assignment

import cats.effect.IO

// Используя IO из библиотеки cats-effect напишите игру "Виселица"
// Пример ввода и тест можно найти в файле src/test/scala/fintech/homework01/HangmanTest.scala
// Тест можно запустить через в IDE или через sbt (написав в консоли sbt test)

// Правила игры "Виселица"
// 1) Загадывается слово
// 2) Игрок угадывает букву
// 3) Если такая буква есть в слове - они открывается
// 4) Если нет - рисуется следующий элемент висельника
// 5) Последней рисуется "веревка". Это означает что игрок проиграл
// 6) Если игрок все еще жив - перейти к пункту 2

// Пример игры:

// Word: _____
// Guess a letter:
// a
// Word: __a_a
// Guess a letter:
// b
// +----
// |
// |
// |
// |
// |

// и т.д.


class Hangman(console: Console[IO]) {
  val stages = Hangman.stages

  def play(word: String): IO[Unit] = {
    val set = word.toSet
    gameLoop(word, set, stages, oldIsFirst = true)
  }

  def winLoop(word: String): IO[Unit] = {
    for {
      _ <- console.printLine("Word: " + word)
      _ <- console.printLine("GG")
    } yield ()
  }

  def readLetter(word: String, oldSet: Set[Char]): IO[Char] = {
    for {
      _ <- console.printLine("Word: " + word.map(c => if (oldSet.contains(c)) '_' else c))
      _ <- console.printLine("Guess a letter:")
      letter <- console.readLine
      endLetter <- {
        if (letter.length > 1) readLetter(word, oldSet)
        else letter.headOption.fold(readLetter(word, oldSet))(IO.pure)
      }
    } yield endLetter
  }

  def gameLoop(word: String, oldSet: Set[Char], oldStages: List[String], oldIsFirst: Boolean): IO[Unit] = {
    if (oldSet.isEmpty) winLoop(word)
    else for {
      letter <- readLetter(word, oldSet)
      correctChoise = word.contains(letter)
      stages = oldStages match {
        case x if correctChoise || oldIsFirst => x
        case _ :: xs => xs
        case Nil => oldStages
      }
      isFirst = correctChoise && oldIsFirst
      set = oldSet - letter
      _
        <- stages match {
        case Nil => IO.unit
        case x :: xs =>
          for {
            _ <- if (!isFirst) console.printLine(x) else IO.unit
            _ <- xs match {
              case Nil =>
                if (correctChoise) gameLoop (word, set, stages, isFirst)
                else console.printLine ("You are dead")
              case _ => gameLoop (word, set, stages, isFirst)
            }
          } yield()
      }
    } yield()
  }
}

object Hangman {

  val stages = List(
    """+----
      ||
      ||
      ||
      ||
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||
      ||
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||   |
      ||
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||   |
      ||  /
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||   |
      ||  / \
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||  /|
      ||  / \
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||  /|\
      ||  / \
      ||
      |""".stripMargin,
    """+----
      ||   |
      ||   O
      ||  /|\
      ||  / \
      ||
      |""".stripMargin
  )
}
