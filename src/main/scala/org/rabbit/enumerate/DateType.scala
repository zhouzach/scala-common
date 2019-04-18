package org.rabbit.enumerate

object DateType extends Enumeration {
  type DateType = Value
  val DAY, MONTH, YEAR = Value

  def getDates(dateType: String, begin: String, end: String) = {

    DateType.withName(dateType.toUpperCase()) match {
      case DateType.DAY =>
      case DateType.MONTH =>
      case DateType.YEAR =>
      case _ => Seq.empty
    }
  }
}
