package switches.model

import java.time.ZonedDateTime

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DateSwitchSpec extends AnyWordSpec with Matchers {

  "isActive" should {
    "setAs" should {
      "set isActive of switch" when {
        "set to true" in {
          val switch = new DateSwitch("switch", ZonedDateTime.now())
          switch.setAs(true)
          switch.isActive shouldBe true
        }
        "set to false" in {
          val switch = new DateSwitch("switch", ZonedDateTime.now())
          switch.setAs(false)
          switch.isActive shouldBe false
        }
      }
    }
    "return true" when {
      "there is no override and set feature date is in the past" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().minusYears(1))
        switch.isActive shouldBe true
      }
      "there is an override and set feature date is in the future" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().plusYears(1))
        switch.setAs(true)
        switch.isActive shouldBe true
      }
    }
    "return false" when {
      "there is no override and set feature date is in the future" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().plusYears(1))
        switch.isActive shouldBe false
      }
      "there is an override and set feature date is in the past" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().minusYears(1))
        switch.setAs(false)
        switch.isActive shouldBe false
      }
    }
  }
  "reset" should {
    "set isActive to false" when {
      "override made it run true" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().plusYears(1))
        switch.setAs(true)
        switch.isActive shouldBe true
        switch.reset()
        switch.isActive shouldBe false
      }
    }
    "set isActive to true" when {
      "override made it run false" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().minusYears(1))
        switch.setAs(false)
        switch.isActive shouldBe false
        switch.reset()
        switch.isActive shouldBe true
      }
    }
    "do nothing if no override is in place" when {
      "switch evaluates to false" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().plusYears(1))
        switch.isActive shouldBe false
        switch.reset()
        switch.isActive shouldBe false
      }
      "switch evaluates to true" in {
        val switch = new DateSwitch("switch", ZonedDateTime.now().minusYears(1))
        switch.isActive shouldBe true
        switch.reset()
        switch.isActive shouldBe true
      }
    }
  }
}
