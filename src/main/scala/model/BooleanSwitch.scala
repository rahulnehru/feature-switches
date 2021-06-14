package model

import java.util.concurrent.atomic.AtomicBoolean

case class BooleanSwitch(name: String, defaultState: Boolean) extends Switch with Override {
  protected val isOn: AtomicBoolean = new AtomicBoolean(defaultState)

  override def isActive: Boolean = isOn.getAcquire
  override def setAs(activationStatus: Boolean): Unit = isOn.set(activationStatus)
  override def reset(): Unit = isOn.set(defaultState)
}

object BooleanSwitch {
  def apply(name: String, defaultState: String): BooleanSwitch = BooleanSwitch(name: String, defaultState.toLowerCase.toBoolean)
}



