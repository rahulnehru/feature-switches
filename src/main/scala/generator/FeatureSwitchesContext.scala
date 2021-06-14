package generator

import model.Switch


class FeatureSwitches(switches: List[Switch]) extends Dynamic {

  import scala.language.dynamics
  // one-arg auxiliary constructor
  def this(path: String) = {
    this(new SwitchBuilder().loadPath(path))
  }

  def selectDynamic(name: String): Switch = {
    switches.find(_.name == name).getOrElse(throw new SwitchNotFoundException(s"Could not find switch with name $name"))
  }
}

class SwitchNotFoundException(message: String) extends Exception(message)
