#Feature Switches

##Overview

A simple Scala library that allows development teams to manage feature toggles in a simple way.
This provides two types of switches that can be implemented using HOCON configuration:

* Date based switches (i.e. a feature should go live at a point in time)
* Boolean switches (i.e. a feature is simply on/off)

##Usage
 
Create a configuration file e.g. `application.conf` and ensure it is available in your classpath. 
You may want to even split out your feature switches entirely into a dedicated `features.conf`.

This library supports HOCON. Your features therefore can be configured as the following:

```hocon
context {

  switchA = true
  switchB = "2020-01-01T00:00:00.000Z"

  multilevel {
    switchC = false
  }
  switchD = "true"
  switchE = "false"
  switchE = "true"
}
``` 

Next, to use this in your application code base. Instantiate a new `FeatureSwitches` object. Ensure this is a singleton in your code base at the risk of overriding this.

```scala
object Main extends App {
  val switches = new FeatureSwitches("context")
}
```

You can then dynamically each individual switch in this object through the use of dynamic methods:

```scala
object Main extends App {
  val switches = new FeatureSwitches("context")
  
  if(switches.switchA.isActive) {
    println("Switch A is on")
  } else {
    println("Switch A is off")
  }
}
```

These feature switches are overrideable for testing purposes:
```scala
object Main extends App {
  val switchA = new FeatureSwitches("context").switchA
  switchA.setAs(true)
  switchA.reset()
}
```