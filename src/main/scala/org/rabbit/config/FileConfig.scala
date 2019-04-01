package org.rabbit.config

import java.io.File

import com.typesafe.config.{Config, ConfigFactory, ConfigRenderOptions}

object FileConfig {

  private val fileConf = ConfigFactory.parseFile(new File("./application.conf"))

  val default = ConfigFactory.load() //default environment

  val combinedConfig: Config = fileConf.withFallback(default)

  val mysqlConfig: Config = combinedConfig.getConfig("oracle")

  val indexTables = combinedConfig.getStringList("index")

  print("oracle ")
  printConf(mysqlConfig)
  def printConf(config: Config): Unit = println(config.root()
    .render(ConfigRenderOptions.concise().setFormatted(true).setJson(true)))

}
