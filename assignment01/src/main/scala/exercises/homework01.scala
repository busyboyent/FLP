package exercises

// Используя функции io.readLine и io.printLine напишите игру "Виселица"
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

class Hangman(io: IODevice) {

  def gameState(letter: Char, current: String, word: String): String ={
    val newCurrent = for (i <- word.indices) yield
      if (letter == word(i))
        word(i)
      else
        current(i)
    newCurrent.mkString
  }

  def play(word: String): Unit = {
    var stage = -1
    var current = "_" * word.length()

    while (stage < stages.length - 1) {

      if (stage >= 0)
        io.printLine(stages(stage))

      io.printLine("Word: " + current)
      io.printLine("Guess a letter:")
      val letter = io.readLine()

      if (letter.length == 1 && letter(0).isLetter) {
        val newCurrent = gameState(letter(0), current, word)
        if (current == newCurrent)
          stage += 1
        else
          current = newCurrent
      }
      else
        io.printLine("enter one letter")
      if (current == word) {
        if (stage >= 0)
          io.printLine(stages(stage))
        io.printLine("Word: " + word)
        io.printLine("GG")
        stage += 100
      }
    }
    if (stage == stages.length - 1)
      io.printLine(stages.last + "You are dead")
  }


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

trait IODevice {
  def printLine(text: String): Unit
  def readLine(): String
}
