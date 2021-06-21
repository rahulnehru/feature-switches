package switches.model

trait Switch extends Override {
  val name: String
  def isActive: Boolean
}

sealed trait Override {
  def setAs(activationStatus: Boolean): Unit
  def reset(): Unit
}
