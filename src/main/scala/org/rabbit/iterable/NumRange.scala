package org.rabbit.iterable

object NumRange {

  def main(args: Array[String]): Unit = {
    println(1 until 5)
    println(1 to 5)

    println(1 to 1)
    println(1 until 1)

    println(2 to 1)
    println(2 until 1)
    val range = 2 until 1
    println(range.size)
  }

}
