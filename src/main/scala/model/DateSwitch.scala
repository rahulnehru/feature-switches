package model

import java.time.ZonedDateTime
import java.util.concurrent.atomic.AtomicBoolean

case class DateSwitch(name: String, activationDate: ZonedDateTime) extends Switch with Override {
  private val hasOverride: AtomicBoolean = new AtomicBoolean(false)
  private val overrideValue: AtomicBoolean = new AtomicBoolean(false)

  override def isActive: Boolean = {
    if (hasOverride.get()) {
      overrideValue.get()
    } else {
      !ZonedDateTime.now().isBefore(activationDate)
    }
  }
  override def setAs(activationStatus: Boolean): Unit = {
    hasOverride.set(true)
    overrideValue.set(activationStatus)
  }

  override def reset(): Unit = hasOverride.set(false)

}

object DateSwitch {
  def apply(name: String, activationDate: String): DateSwitch = {
    DateSwitch(name: String, ZonedDateTime.parse(activationDate))
  }
}






