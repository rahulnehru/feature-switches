import generator.FeatureSwitches

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val switches = new FeatureSwitches("context-switches")
    val featureSwitchX = switches.featureX
    val featureSwitchY = switches.featureY
    print(featureSwitchX)
    print(featureSwitchY)
  }

}