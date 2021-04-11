# IO

## Домашнее задание

Используя IO из библиотеки cats-effect напишите игру "Виселица"

Пример ввода и тест можно найти в файле src/test/scala/assignment/HangmanTest.scala

Тест можно запустить через IDE или через sbt (написав в консоли sbt test)

Заготовка находится в [Hangman.scala](src/main/scala/assignment/Hangman.scala)

В проекте включен линтер, который запрещает использование var/while/throw и прочего.
Чтобы он нормально подхватился в IDEA - не забываем выставлять галочки в File -> Settings... -> sbt
