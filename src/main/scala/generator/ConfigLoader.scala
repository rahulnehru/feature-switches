package generator

import com.typesafe.config.{Config, ConfigFactory}

case object ConfigLoader {
  def load(configPath: String): Config = {
    val config: Config = ConfigFactory.load().getConfig(configPath)
    config.checkValid(config)
    config
  }
}


