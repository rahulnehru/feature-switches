package switches.generator

import java.time.ZonedDateTime.parse

import com.typesafe.config.ConfigValueType.{BOOLEAN, STRING}
import com.typesafe.config.{Config, ConfigFactory, ConfigValue}
import switches.model.{BooleanSwitch, DateSwitch, Switch}

import scala.collection.GenTraversable
import scala.collection.JavaConverters._
import scala.util.Try

protected[generator] final class SwitchBuilder {

  private[generator] implicit val convertToList: Switch => Seq[Switch] = s => s :: Nil
  private[generator] val stripQuotes: String => String = _.replaceAll("\"", "")

  private[generator] def determineSwitchType(name: String, value: ConfigValue): Seq[Switch] = {
    value.valueType() match {
      case BOOLEAN =>
        BooleanSwitch(name, value.render())

      case STRING if stripQuotes(value.render()).matches("true|false") =>
        BooleanSwitch(name, stripQuotes(value.render()))

      case STRING if Try {parse(stripQuotes(value.render()))}.isSuccess =>
        DateSwitch(name, stripQuotes(value.render()))

      case _ =>
        throw new SwitchBuilderException(s"Can't resolve switch: [$name] with value [$value] for config type ${value.valueType()}")
    }
  }

  private[generator] def loadFromConfig(configPath: String): Config = {
    val config: Config = ConfigFactory.load().getConfig(configPath)
    config.checkValid(config)
    config
  }

  private[generator] def mapConfigToList(f: java.util.Map.Entry[String, ConfigValue] => GenTraversable[Switch]): Config => Seq[Switch] = c => {
    c.entrySet().asScala.flatMap(f).toSeq
  }

  def build(configPath: String): Seq[Switch] = {
    mapConfigToList(kv => determineSwitchType(kv.getKey, kv.getValue)) // Instantiate function
    .apply(loadFromConfig(configPath)) // Load config
    .foldLeft(Seq.empty[Switch]) {(acc, s: Switch) => if (acc.exists(_.name == s.name)) acc else acc :+ s } // Deduplicate
  }
}

class SwitchBuilderException(message: String) extends Exception(message)

