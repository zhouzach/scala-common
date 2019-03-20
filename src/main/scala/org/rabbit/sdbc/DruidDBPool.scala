package org.rabbit.sdbc

import java.sql.{Connection, ResultSet, SQLException}

import com.alibaba.druid.pool.DruidDataSource
import org.rabbit.config.FileConfig
import org.rabbit.sdbc.C3P0Tools.{close, logger}

import scala.collection.mutable.Map

object DruidDBPool {

  def getDataSource(url: String, className: String, userName: String, password: String): DruidDataSource = {
    val dataSource = new DruidDataSource()
    dataSource.setUrl(url)
    dataSource.setDriverClassName(className)
    dataSource.setUsername(userName)
    dataSource.setPassword(password)


    dataSource.setInitialSize(10)
    dataSource.setMinIdle(10)
    dataSource.setMaxActive(100)

    dataSource.setMaxWait(60000)
    dataSource.setTimeBetweenEvictionRunsMillis(60000)
    dataSource.setMinEvictableIdleTimeMillis(300000)

    dataSource.setValidationQuery("SELECT 'x'")
    dataSource.setTestWhileIdle(true)
    dataSource.setTestOnBorrow(false)
    dataSource.setTestOnReturn(false)

    dataSource.setPoolPreparedStatements(false)
    dataSource.setMaxPoolPreparedStatementPerConnectionSize(10)
    dataSource.setFilters("stat")

    dataSource
  }

  def execQuery[T](connection: Option[Connection], query: String)(function: Option[ResultSet] => T) = {
    try {
      if(connection.nonEmpty) {
        val pstmt = connection.get.prepareStatement(query)
        val rs = pstmt.executeQuery()
        function(Some(rs))
      } else {
        function(None)
      }
    }catch {
      case sqlExp: SQLException =>
        sqlExp.printStackTrace()
        logger.info(sqlExp.toString)
        function(None)
      case exp: Exception =>
        exp.printStackTrace()
        logger.info(exp.toString)
        function(None)
    }finally {
      connection match {
        case Some(conn) => close(connection.get)
        case None =>
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val mysqlConfig = FileConfig.mysqlConfig
    val url = mysqlConfig.getString("url")
    val driverClassName = mysqlConfig.getString("driverClassName")
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
