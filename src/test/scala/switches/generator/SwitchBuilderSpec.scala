package switches.generator

import java.time.ZonedDateTime

import com.typesafe.config.ConfigException
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import switches.model.{BooleanSwitch, DateSwitch}

class SwitchBuilderSpec extends AnyWordSpec with Matchers {

  private val sb = new SwitchBuilder()
  "convertToList" should {
    "return a list of switches" when {
      "passed a switch" in {
        val switch = new BooleanSwitch("switchname", true)
        val result = sb.convertToList(switch)
        result should have size 1
        result.head.name shouldBe switch.name
        result.head.isActive shouldBe switch.isActive
      }
    }
  }

  "stripQuotes" should {
    "return original string" when {
      "string does not contain any quotes" in {
        val s = "hello"
        sb.stripQuotes(s) shouldBe "hello"
      }
    }
    "strip quotes from original string" when {
      "it contains quotes" in {
        val s = "\"hello\""
        sb.stripQuotes(s) shouldBe "hello"
      }
    }
  }

  "load" should {
    "collect top level switches" when {
      "top level is defined" in {
        val t = sb.loadFromConfig("context")
        t.isEmpty shouldBe false
      }
      "top level is defined as an object but empty" in {
        val t = sb.loadFromConfig("emptycontext")
        t.isEmpty shouldBe true
      }
    }
    "throw exception" when {
      "top level is defined" in {
        assertThrows[ConfigException](sb.loadFromConfig("context2"))
      }
    }
  }

  "loadPath" should {
    "load all config from config file" in {
      val switches = sb.build("context")
      switches should have size 5
      switches should contain(new BooleanSwitch("switchA", true))
      switches should contain(new DateSwitch("switchB", ZonedDateTime.parse("2020-01-01T00:00:00.000Z")))
      switches should contain(new BooleanSwitch("multilevel.switchC", false))
      switches should contain(new BooleanSwitch("switchE", true))
    }
  }
}
