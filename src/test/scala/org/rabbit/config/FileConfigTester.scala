package org.rabbit.config

object FileConfigTester {

  def main(args: Array[String]): Unit = {
    val  indexTables = FileConfig.indexTables

    import scala.collection.JavaConversions._
    for (row <- indexTables ) {

      val data = row.split(":")
      val table = data(0)
      val cols = data(1).split(",")
      print(table + ": ")
      cols.foreach(print(_))
      println()

    }
  }


}
