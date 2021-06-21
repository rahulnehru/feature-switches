package switches.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BooleanSwitchSpec extends AnyWordSpec with Matchers {

  "isActive" should {
    "setAs" should {
      "set isActive of switch" when {
        "set to true" in {
          val switch = new BooleanSwitch("switch", false)
          switch.setAs(true)
          switch.isActive shouldBe true
        }
        "set to false" in {
          val switch = new BooleanSwitch("switch", true)
          switch.setAs(false)
          switch.isActive shouldBe false
        }
      }
    }
    "return true" when {
      "there is no override and default is true" in {
        val switch = new BooleanSwitch("switch", true)
        switch.isActive shouldBe true
      }
      "there is an override and default is false" in {
        val switch = new BooleanSwitch("switch", false)
        switch.setAs(true)
        switch.isActive shouldBe true
      }
    }
    "return false" when {
      "there is no override and default is false" in {
        val switch = new BooleanSwitch("switch", false)
        switch.isActive shouldBe false
      }
      "there is an override and default is true" in {
        val switch = new BooleanSwitch("switch", true)
        switch.setAs(false)
        switch.isActive shouldBe false
      }
    }
  }
  "reset" should {
    "set isActive to false" when {
      "override made it run true" in {
        val switch = new BooleanSwitch("switch", false)
        switch.setAs(true)
        switch.isActive shouldBe true
        switch.reset()
        switch.isActive shouldBe false
      }
    }
    "set isActive to true" when {
      "override made it run false" in {
        val switch = new BooleanSwitch("switch", true)
        switch.setAs(false)
        switch.isActive shouldBe false
        switch.reset()
        switch.isActive shouldBe true
      }
    }
    "do nothing if no override is in place" when {
      "switch evaluates to false" in {
        val switch = new BooleanSwitch("switch", false)
        switch.isActive shouldBe false
        switch.reset()
        switch.isActive shouldBe false
      }
      "switch evaluates to true" in {
        val switch = new BooleanSwitch("switch", true)
        switch.isActive shouldBe true
        switch.reset()
        switch.isActive shouldBe true
      }
    }
  }

}
