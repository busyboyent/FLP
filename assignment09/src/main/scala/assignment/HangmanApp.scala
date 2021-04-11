package assignment

import cats.effect.{ExitCode, IO, IOApp}

import scala.io.StdIn

object HangmanApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    val console = new Console[IO] {
      override def printLine(text: String): IO[Unit] = IO(println(text))
      override def readLine: IO[String] = IO(StdIn.readLine())
    }

    new Hangman(console).play("KEK")
      .map(_ => ExitCode.Success)
  }
}
