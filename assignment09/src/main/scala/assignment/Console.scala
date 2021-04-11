package assignment

trait Console[F[_]] {
  def printLine(text: String): F[Unit]

  def readLine: F[String]
}