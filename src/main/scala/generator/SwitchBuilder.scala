package generator

import java.time.ZonedDateTime.parse

import com.typesafe.config.{ConfigValue, ConfigValueType}
import model.{BooleanSwitch, DateSwitch, Switch}

import scala.util.Try

protected[generator] class SwitchBuilder {
  import scala.collection.JavaConverters._

  private def createSwitch(name: String, value: ConfigValue): List[Switch] = {
    val stripQuotes: String => String = _.replaceAll("\"", "")
    implicit val convertToList: Switch => List[Switch] = s => s :: Nil
    value.valueType() match {
      case ConfigValueType.BOOLEAN => BooleanSwitch(name, value.render())
      case ConfigValueType.STRING if stripQuotes(value.render()).matches("true|false") => BooleanSwitch(name, stripQuotes(value.render()))
      case ConfigValueType.STRING if Try {parse(stripQuotes(value.render()))}.isSuccess =>
        DateSwitch(name, stripQuotes(value.render()))
      case ConfigValueType.OBJECT => value.atPath(name).entrySet().asScala.flatMap(kv => createSwitch(kv.getKey, kv.getValue)).toList
      case _ => throw new Exception(s"Can't parse $name with value $value for config type ${value.valueType()}")
    }
  }

  def loadPath(configPath: String): List[Switch] = {
    val config = ConfigLoader.load(configPath)
    val listOfSwitches: List[Switch] = config.entrySet().asScala.flatMap { kv => createSwitch(kv.getKey, kv.getValue) }.toList
    listOfSwitches.foldLeft(List.empty[Switch]){(acc, s: Switch) => if (acc.exists(_.name == s.name)){ acc } else {acc :+ s }}
  }
}



