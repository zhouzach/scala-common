package org.rabbit.sdbc

import java.sql.ResultSet

import org.rabbit.config.FileConfig

import scala.collection.mutable.Map

object DruidTester {


  def main(args: Array[String]): Unit = {

    val mysqlConfig = FileConfig.mysqlConfig
    val driverClassName = mysqlConfig.getString("driverClassName")
    val url = mysqlConfig.getString("url")
    val username = mysqlConfig.getString("username")
    val password = mysqlConfig.getString("password")

    val sql = "select * from XXL_JOB_QRTZ_CRON_TRIGGERS"


    val dataSource = DruidDBPool.getDataSource(url, driverClassName, username, password)


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

    DruidDBPool.execQuery(Some(dataSource.getConnection()), sql)(fun2CalN).foreach {
      case (k, v) =>
        println(k)
        println(v)
    }
  }


}
