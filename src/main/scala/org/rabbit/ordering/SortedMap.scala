package org.rabbit.ordering

object SortedMap {

  def main(args: Array[String]): Unit = {


    val map = Map("a" -> 1, "b" -> 2, "c" -> 5, "d" -> 4, "e" -> 3)
    map.toSeq.sortBy(_._2).foreach(println(_))
    println()
    map.toSeq.sortBy(-_._2).foreach(println(_))
  }

}
