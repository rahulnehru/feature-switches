package switches.generator

import switches.model.Switch

final class FeatureSwitches(switches: Seq[Switch]) extends Dynamic {
  import scala.language.dynamics
  def this(path: String) = {
    this(new SwitchBuilder().build(path))
  }

  def selectDynamic(name: String): Switch = {
    switches.find(_.name == name).getOrElse(throw new SwitchNotFoundException(s"Could not find switch with name $name"))
  }
}

class SwitchNotFoundException(message: String) extends Exception(message)
