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

  def formateDate(date: String) = {
    val dt = new DateTime(date).toString("yyyy-MM-dd")
    new DateTime(date).toString("yyyy-MM-dd HH:mm:ss")
    println(dt)
  }

  def formateDate2yyyyMM(date: String) = {
    val d = new DateTime(date).toString("yyyy-MM").replaceAll("-", "")
    println(d)
  }

  def getBeijingTime(date: String) = {
    val bjt = new DateTime(date).withZone(DateTimeZone.UTC)
      .withZone(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd")
    println(bjt)
  }

  def getYear(date: String): Int = {
    LocalDate.parse(date).getYear
  }

  def getMonth(date: String): Int = {
    LocalDate.parse(date).getMonthOfYear
  }

  def getDay(date: String): Int = {
    LocalDate.parse(date).getDayOfMonth
  }

  def getStartTime(date: String) = {
    val startTime = new DateTime(date).withTimeAtStartOfDay()
    println(startTime)
  }

  def getEndTime(date: String) = {
    val endTime = new DateTime(date).plusDays(1).withTimeAtStartOfDay().plusSeconds(1)
    println(endTime)
  }

  def getNewYearDay(date: String) = {
    val startDay = LocalDate.parse(date).withMonthOfYear(1).withDayOfMonth(1)
    println(startDay)
  }

  def get12thMonth(date: String) = {

    val mon = LocalDate.parse(date).withMonthOfYear(1).withDayOfMonth(1).minusDays(1)
      .getMonthOfYear
    println(mon)
  }

  def get1YearAgo(date: String) = {
    val ayear = new DateTime(date).minusYears(1)
      .withZone(DateTimeZone.UTC).withZone(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd")
    println(ayear)
  }

  def main(args: Array[String]): Unit = {
    //    getDaysBetweenDate("2018-01-06", "2018-01-07").foreach(println(_))
    //
    //    println()
    //    getMonthsBetweenDate("2018-01", "2018-04").foreach(println(_))
    //
    //    println()
    //    getYearsBetweenDate("2016-10-20", "2018-04-10").foreach(println(_))

    val date = "2019-08-31"

    get1YearAgo(date)

//    println(
//      s"""
//         |in Tag trait:
//         |
//         |date: $date
//         |
//         |beginDate: $beginDate
//         |beginYear: $beginYear
//         |beginMonth1: $beginMonth1
//         |beginMonth2: $beginMonth2
//         |
//         |endYear: $endYear
//         |endMonth1: $endMonth1
//         |endMonth2: $endMonth2
//         |
//         |orderTimeEndDate: $orderTimeEndDate
//         |
//         |cdpOrderBeginDate: $cdpOrderBeginDate
//         |cdpOrderEndDate: $cdpOrderEndDate
//         |
//         |date1m: $date1m
//         |year1m: $year1m
//         |month1m: $month1m
//         |
//         |""".stripMargin)


  }

  def getDates(date: String, n: Int) = {

    val date1m = new DateTime(date)
      .plusMonths(1)
      .withZone(DateTimeZone.UTC).withZone(DateTimeZone.forOffsetHours(8))
    val year1m = date1m.getYear
    val month1m = date1m.getMonthOfYear

    val dt = new DateTime(date).plusMonths(n)
    if (dt.getYear > new DateTime(date).getYear) {

      val month12th = dt.withMonthOfYear(1).withDayOfMonth(1).minusDays(1)
        .withZone(DateTimeZone.UTC).withZone(DateTimeZone.forOffsetHours(8))
        .getMonthOfYear

      val eYear = dt
        .withZone(DateTimeZone.UTC).withZone(DateTimeZone.forOffsetHours(8))
        .getYear
      val eMonth1 = dt.withMonthOfYear(1).withDayOfMonth(1)
        .withZone(DateTimeZone.UTC).withZone(DateTimeZone.forOffsetHours(8))
        .getMonthOfYear
      val eMonth2 = dt
        .withZone(DateTimeZone.UTC).withZone(DateTimeZone.forOffsetHours(8))
        .getMonthOfYear

      s"""
         |
         |((order_year = $year1m and order_month between $month1m and $month12th)
         |or (order_year = $eYear and order_month between $eMonth1 and $eMonth2))
         |""".stripMargin

    } else {

      s"""
         |order_year = ${dt.getYear}  and order_month between $month1m and ${dt.getMonthOfYear}
         |""".stripMargin
    }
  }

}
