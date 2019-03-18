package org.rabbit.sdbc

import java.sql.{Connection, ResultSet, SQLException}

import com.alibaba.druid.pool.DruidDataSource
import org.rabbit.sdbc.C3P0Tools.{close, logger}

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


}
