package org.rabbit.enumerate

import org.joda.time._


/**
  * https://www.joda.org/joda-time/key_format.html
  * https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
  */

object DateUtil {

  object DateType extends Enumeration {
    //    type DateType = Value
    val DAY, WEEK, MONTH, QUARTER, HALFOFYEAE, YEAR = Value
  }

  def getDates(dateType: String, begin: String, end: String) = {

    DateType.withName(dateType.toUpperCase()) match {
      case DateType.DAY =>
        getDaysBetweenDate(begin, end)

      case DateType.WEEK =>
        getWeeksBetweenDate(begin, end)

      case DateType.MONTH =>
        getMonthsBetweenDate(begin, end)

      case DateType.QUARTER =>
        getQuartersBetweenDate(begin, end)

      case DateType.HALFOFYEAE =>
        begin :: end :: Nil

      case DateType.YEAR =>
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

    val days = Days.daysBetween(beginDate, endDate).getDays
    Months.monthsBetween(beginDate, endDate)

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
    * 获取两个日期间隔的所有月份
    *
    * @param start 格式必须为'2018-01-25'
    * @param end   格式必须为'2018-01-25'
    * @return
    */
  def getQuartersBetweenDate(begin: String, end: String) = {
    var dates: Seq[String] = Seq.empty

    val year = begin.substring(0, 4)

    val b = begin.substring(4)
    val i = Integer.parseInt(b)

    val e = end.substring(4)
    val ei = Integer.parseInt(e)

    (i to ei).foreach { quarter =>
      dates = dates :+ year + "-" + String.valueOf(quarter)
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
  def getWeeksBetweenDate(begin: String, end: String) = {
    var dates: Seq[String] = Seq.empty

    val beginDate = LocalDate.parse(begin)
    val endDate = LocalDate.parse(end)

    val weeks = Weeks.weeksBetween(beginDate, endDate).getWeeks

    if (weeks >= 0) {
      val beginWeek = beginDate.toString("yyyy-ww")
      dates = dates :+ String.valueOf(beginWeek)

      (1 to weeks).foreach { week =>
        dates = dates :+ String.valueOf(beginDate.plusWeeks(week).toString("yyyy-ww"))
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
      val beginYear = beginDate.toString("yyyy")
      dates = dates :+ String.valueOf(beginYear)

      (1 to years).foreach { year =>
        dates = dates :+ String.valueOf(beginDate.plusYears(year).toString("yyyy"))
      }
    }

    dates
  }


  def main(args: Array[String]): Unit = {

    println(Integer.parseInt("null"))
//    val m =new DateTime("2018-01-31").plusMonths(0)
//    println(m)
    //    val b = "20182".substring(4)
    //    val i = Integer.parseInt(b)
    //
    //
    //    val e = "20182".substring(4)
    //    val ei = Integer.parseInt(e)
    //
    //    (i to ei).foreach(println(_))

//    getDates("quarter", "20183", "20183").foreach(println(_))
    //     val ww= LocalDate.parse("2018-03-31").toString("yyyy-ww")
    //    println(ww)

    //    getDates("week", "2018-01-31", "2018-02-09").foreach(println(_))
    //    getDates("day", "2018-01-31", "2018-02-02").foreach(println(_))
    //
    //    getDates("month", "2018-01-26", "2018-06-07").foreach(println(_))
    //
    //    getDates("year", "2018-01-26", "2028-06-07").foreach(println(_))
  }

}
