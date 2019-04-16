package org.rabbit.config

import java.io.File
import java.util.Map.Entry
import java.util.concurrent.ConcurrentHashMap

import com.typesafe.config.{Config, ConfigFactory, ConfigValue}
import org.slf4j.LoggerFactory


trait ClientConf[C <: ClientConf[C]] {

  val configs: ConcurrentHashMap[String, Any]

  val logger = LoggerFactory.getLogger(this.getClass)

  val SERVICE_CONF = "application.conf"

  protected def getConfigs(key: String, confFile: String = SERVICE_CONF): ConcurrentHashMap[String, Any] = {
    val conf = new ConcurrentHashMap[String, Any]()
    val agentConfig = loadFromResource(key, confFile)
    for (entry <- agentConfig.entrySet().toArray) {
      val obj = entry.asInstanceOf[Entry[String, ConfigValue]]
      conf.put(obj.getKey, obj.getValue.unwrapped())
    }
    conf
  }

  def get[V](key: String): Option[V] = {
    configs.get(key) match {
      case x: V => Some(x)
      case _ => None
    }
  }

  private def loadFromResource(key: String, conf: String): Config = {
    val file = new File(".", conf)
    if (file.exists()) {
      loadConfig(file).getConfig(key)
    } else {
      ConfigFactory.load(conf).getConfig(key)
    }
  }

  private def loadConfig(file: File): Config = {
    ConfigFactory.parseFile(file)
  }
}