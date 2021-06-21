package switches.generator

import java.time.ZonedDateTime

import com.typesafe.config.ConfigException.Missing
import switches.model.{BooleanSwitch, DateSwitch}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import switches.generator.SwitchNotFoundException

import scala.util.Try

class FeatureSwitchesSpec extends AnyWordSpec with Matchers {

  private def doesNotThrow(f: () => Unit) = Try{f}.isSuccess shouldBe true

  "constructor" should {
    "load switches" when {
      "context path is available" in {
        doesNotThrow(() => new FeatureSwitches("context"))
      }
    }
    "throw exception" when {
      "context path is not available" in {
        assertThrows[Missing](new FeatureSwitches("badContext"))
      }
    }
  }

  "selectDynamic" should {
    "allow feature switches to be directly called" when {
      "they are defined" in {
        val switches = new FeatureSwitches("context")
        val a = switches.switchA
        val b = switches.switchB
        val c = switches.`multilevel.switchC`
        a shouldBe new BooleanSwitch("switchA", true)
        b shouldBe new DateSwitch("switchB", ZonedDateTime.parse("2020-01-01T00:00:00.000Z"))
        c shouldBe new BooleanSwitch("multilevel.switchC", false)
      }
    }
    "throw an exception" when {
      "switch cannot be found" in {
        val switches = new FeatureSwitches("context")
        assertThrows[SwitchNotFoundException](switches.featureSwitchNotDefined)
      }
    }
  }

}
