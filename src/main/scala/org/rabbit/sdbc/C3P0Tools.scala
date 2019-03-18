package org.rabbit.sdbc

import java.sql.{Connection, ResultSet, SQLException}

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.typesafe.config.{Config, ConfigFactory, ConfigRenderOptions}
import org.rabbit.config.FileConfig
import org.slf4j.LoggerFactory

object C3P0Tools {
  val logger = LoggerFactory.getLogger(getClass)

  val config = ConfigFactory.load()

  val mysqlConfig = FileConfig.mysqlConfig
  val driverClassName = mysqlConfig.getString("driverClassName")
  val url = mysqlConfig.getString("url")
  val username = mysqlConfig.getString("username")
  val password = mysqlConfig.getString("password")

//  val initialPoolSize = config.getInt("ctx.dataSource.initialPoolSize")
//  val maxPoolSize = config.getInt("ctx.dataSource.maxPoolSize")
//  val minPoolSize = config.getInt("ctx.dataSource.minPoolSize")
//  val maxStatements = config.getInt("ctx.dataSource.maxStatements")

  val dataSource = new ComboPooledDataSource()
  dataSource.setDriverClass(driverClassName)
  dataSource.setJdbcUrl(url)
  dataSource.setUser(username)
  dataSource.setPassword(password)

//  dataSource.setInitialPoolSize(initialPoolSize)
//  dataSource.setMaxPoolSize(maxPoolSize)
//  dataSource.setMinPoolSize(minPoolSize)
//  dataSource.setMaxStatements(maxStatements)
  println(dataSource)
  printConf(mysqlConfig)
  def printConf(config: Config): Unit = println(config.root().render(ConfigRenderOptions.concise().setFormatted(true).setJson(true)))


  def getDataSource = {
    dataSource
  }
  def getConnection(): Option[Connection] = {
    try {
      Some(dataSource.getConnection)
    } catch {
      case sqlExp: SQLException =>
        sqlExp.printStackTrace()
        logger.info(sqlExp.toString)
        None
      case exp: Exception =>
        exp.printStackTrace()
        logger.info(exp.toString)
        None
    }
  }

  def close(connection: Connection) = {
    if(connection != null) {
      try {
        connection.close()
      }catch {
        case sqlExp: SQLException =>
          sqlExp.printStackTrace()
          logger.info(sqlExp.toString)
        case exp: Exception =>
          exp.printStackTrace()
          logger.info(exp.toString)
      }
    }
  }

  def execQuery[T](connection: Option[Connection], query: String)(function: Option[ResultSet] => T) = {
    try {
      if(connection.nonEmpty) {
//        val statement = connection.get.createStatement()
//        val rs = statement.executeQuery(query)
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

  def execUpdate[T](connection: Option[Connection], updateStr: String) = {
    try {
      if(connection.nonEmpty) {
//        val statement = connection.get.createStatement()
//        statement.executeUpdate(updateStr)
        val pstmt = connection.get.prepareStatement(updateStr)
        val rs = pstmt.executeUpdate()
      }
    }catch {
      case sqlExp: SQLException =>
        sqlExp.printStackTrace()
        logger.info(sqlExp.toString)
      case exp: Exception =>
        exp.printStackTrace()
        logger.info(exp.toString)
    }finally {
      connection match {
        case Some(conn) => close(connection.get)
        case None =>
      }
    }
  }
}
