package assignment

import cats.data.{NonEmptyList => NEL}
import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.instances.list._
import cats.syntax.all._

import scala.util.Properties

object TestIOConsole {
  def create(base: String): IO[TestIOConsole] = {
    Ref[IO].of(0).map(r =>
      new TestIOConsole(r, base.linesIterator.toList))
  }
}

class TestIOConsole(ref: Ref[IO, Int], list: List[String]) extends Console[IO] {

  val inputFormat = "> (.*)".r

  def error(m: String): Exception =
    new IllegalStateException(
      s"""${m}
Test data:
${list.zipWithIndex.map { case (line, i) => s"${i}\t: $line" }.mkString("\n")}"""
    )

  def readLine: IO[String] = for {
    index <- ref.get
    _ = println("> ")
    nextLine <- list.lift(index)
      .liftTo[IO](error(s"Unexpected read at line $index, no more data"))
    read <- nextLine match {
      case inputFormat(read) => IO.pure(read)
      case write =>
        IO.raiseError(error(s"Unexpected read at line $index, expected printout of '${write}''"))
    }
    _ <- ref.set(index + 1)
  } yield read

  def printLine(text: String): IO[Unit] = {

    println(s""""$text"""")


    val lines = text.split(Properties.lineSeparator, -1).toList

    def printOneLine(line: String): IO[Unit] = for {
      index <- ref.get
      _ = println(line)
      nextLine <- list.lift(index)
        .liftTo[IO](error(s"Unexpected printout it line $index, no more data"))
      write <- nextLine match {
        case inputFormat(read) =>
          IO.raiseError(error(s"Unexpected printout of '$line' at line $index," +
            s" expected read of '$read'"))
        case write => IO.pure(write)
      }
      _ <- if (write != line)
        IO.raiseError(error(s"Unexpected printout of '$line' at line $index," +
          s" expected printout of '$write'"))
      else IO.unit

      _ <- ref.set(index + 1)
    } yield ()

    lines.traverse(printOneLine).void
  }

}
