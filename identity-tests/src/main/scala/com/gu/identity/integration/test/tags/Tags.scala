package com.gu.identity.integration.test.tags

import org.scalatest.Tag

  /* Usage: The tag is added as a comma separated list for each scenario after the scenario name
  e.g. scenarioWeb("Example test that is unstable", Small, Unstable)
    Running Tests:  The Run/Debug configuration can specify which tags you want to run or ignore using the tag text
    e.g. to run only small tests but ignore unstable or large tests you would use the following in Test Options
    -n small -l large -l unstable
  */

  //tests intermittently fail, e.g. tests running against 3rd party sites are more reliant on stable connections
  object Unstable extends Tag("unstable")

  //test usually finishes within 60 seconds
  object Small extends Tag("small")

  //test usually finishes after 60 seconds
  object Large extends Tag("large")

