package exercises

class TestIO(base: String) extends IODevice {
  var queue: List[String] = base.linesIterator.toList.tail // skip first line

  def readLine(): String = {
    if (isExhausted)
      throw new IllegalStateException("Unexpected read. Expected no more operations, got read operation.")

    val expected = queue.head
    if (!expected.startsWith("> "))
      throw new IllegalStateException(s"Unexpected read. Expected printout of '$expected', got read operation.")

    queue = queue.tail
    expected.substring(2)
  }

  def printLine(text: String): Unit = {
    for (line <- text.linesIterator) {
      if (isExhausted)
        throw new IllegalStateException(s"Unexpected printout. Expected no more printouts, got printout of '$line'")

      val expected = queue.head
      if (expected.startsWith("> "))
        throw new IllegalStateException(s"Unexpected printout. Expected read of '$expected', got printout of '$line'")

      if (expected != line)
        throw new IllegalStateException(s"Unexpected printout. Expected '$expected', got '$line'")

      queue = queue.tail
    }
  }

  def isExhausted: Boolean = queue.isEmpty

  override def toString: String = "TestIO(queue: " + queue.toString + ")"
}
