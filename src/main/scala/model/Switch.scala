package model

trait Switch {
  val name: String
  def isActive: Boolean

}

private[model] trait Override extends Switch {
  def setAs(activationStatus: Boolean): Unit
  def reset(): Unit
}
