package generator

import java.time.ZonedDateTime
import java.time.ZonedDateTime.parse

import com.typesafe.config.{Config, ConfigFactory, ConfigValue, ConfigValueType}
import model.{BooleanSwitch, DateSwitch}

import scala.util.Try

case object ConfigLoader {
  def load(configPath: String): Config = {
    val config: Config = ConfigFactory.load().getConfig(configPath)
    config.checkValid(config)
    config
  }
}


