package org.rabbit.date

import org.joda.time._

object DateUtil {

  object DayType extends Enumeration {
    type DayType = Value
    val DAY, MONTH, YEAR = Value
  }

  def getDates(dateType: String, begin: String, end: String) = {

    DayType.withName(dateType.toUpperCase()) match {
      case DayType.DAY =>
        getDaysBetweenDate(begin, end)
      case DayType.MONTH =>
        getMonthsBetweenDate(begin, end)
      case DayType.YEAR =>
        getYearsBetweenDate(begin, end)
      case _ => Seq.empty
    }
  }


  /**
    * 获取两个日期间隔的所有日期
    *
    * @param start 格式必须为'2018-01-25'
    * @param end   格式必须为'2018-01-25'
    * @return
    */
  def getDaysBetweenDate(begin: String, end: String) = {
    var dates: Seq[String] = Seq.empty

    val beginDate = LocalDate.parse(begin)
    val endDate = LocalDate.parse(end)

    val days = Days.daysBetween(beginDate, endDate).getDays()

    if (days >= 0) {
      dates = dates :+ String.valueOf(beginDate)

      (1 to days).foreach { day =>
        dates = dates :+ String.valueOf(beginDate.plusDays(day))

      }
    }

    dates
  }


  /**
    * 获取两个日期间隔的所有月份
    *
    * @param start 格式必须为'2018-01-25'
    * @param end   格式必须为'2018-01-25'
    * @return
    */
  def getMonthsBetweenDate(begin: String, end: String) = {
    var dates: Seq[String] = Seq.empty

    val beginDate = LocalDate.parse(begin).withDayOfMonth(1)
    val endDate = LocalDate.parse(end).withDayOfMonth(1)

    val months = Months.monthsBetween(beginDate, endDate).getMonths

    if (months >= 0) {
      val beginMonth = beginDate.toString("yyyy-MM")
      dates = dates :+ String.valueOf(beginMonth)

      (1 to months).foreach { month =>
        dates = dates :+ String.valueOf(beginDate.plusMonths(month).toString("yyyy-MM"))
      }
    }

    dates
  }

  /**
    * 获取两个日期间隔的所有年份
    *
    * @param start 格式必须为'2018-01-25'
    * @param end   格式必须为'2018-01-25'
    * @return
    */
  def getYearsBetweenDate(begin: String, end: String) = {
    var dates: Seq[String] = Seq.empty

    val beginDate = LocalDate.parse(begin).withMonthOfYear(1).withDayOfMonth(1)
    val endDate = LocalDate.parse(end).withMonthOfYear(1).withDayOfMonth(1)

    val years = Years.yearsBetween(beginDate, endDate).getYears

    if (years >= 0) {
      val beginMonth = beginDate.toString("yyyy")
      dates = dates :+ String.valueOf(beginMonth)

      (1 to years).foreach { month =>
        dates = dates :+ String.valueOf(beginDate.plusYears(month).toString("yyyy"))
      }
    }

    dates
  }

  def main(args: Array[String]): Unit = {
    getDaysBetweenDate("2018-01-06", "2018-01-07").foreach(println(_))

    println()
    getMonthsBetweenDate("2018-01", "2018-04").foreach(println(_))

    println()
    getYearsBetweenDate("2016-10-20", "2018-04-10").foreach(println(_))

  }

}
