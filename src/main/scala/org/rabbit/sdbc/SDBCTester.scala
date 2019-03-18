package org.rabbit.sdbc

import java.sql.ResultSet

import scala.collection.mutable.Map

object SDBCTester {


  def main(args: Array[String]): Unit = {

    val conn = C3P0Tools.getConnection()
    val sql = "select * from XXL_JOB_QRTZ_CRON_TRIGGERS"
    val fun2CalN = (rs: Option[ResultSet]) => {

      val map = Map.empty[String, Any]
      rs match {
        case Some(result) =>
          while (result.next()) {
            val name = result.getString("SCHED_NAME")
            //              val age = result.getInt("TRIGGER_GROUP")
            val age = result.getString("TRIGGER_GROUP")
            map += (name -> age)
          }
          map
        case None => map
      }
    }

    C3P0Tools.execQuery(conn, sql)(fun2CalN)
      .foreach{
        case (k,v) =>
          println(k)
          println(v)
      }
  }


}
