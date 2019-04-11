package org.rabbit.enumerate

object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, SAT, Sun = Value


  def isWorkingDay(d: WeekDay) = ! (d == SAT || d == Sun)

  def main(args: Array[String]): Unit = {
    WeekDay.values filter isWorkingDay foreach println

    println(SAT.toString)
  }
}
