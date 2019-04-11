package org.rabbit.enumerate

object DayType extends Enumeration {
  type DayType = Value
  val DAY, MONTH, YEAR = Value

  def getDates(dateType: String, begin: String, end: String) = {

    DayType.withName(dateType.toUpperCase()) match {
      case DayType.DAY =>
      case DayType.MONTH =>
      case DayType.YEAR =>
      case _ => Seq.empty
    }
  }
}
