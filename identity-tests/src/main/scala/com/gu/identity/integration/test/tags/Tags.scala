package com.gu.identity.integration.test.tags

import org.scalatest.Tag

  /* Usage: The tag is added as a comma separated list for each scenario after the scenario name
  e.g. scenarioWeb("Example test that is unstable", Small, Unstable)
    Running tests locally:  The Run/Debug configuration can specify which tags you want to run or ignore using the tag
    text e.g. to run only core tests but ignore unstable or optional tests you would use the following in Test Options
    -n CoreTest -l OptionalTest -l Unstable
    Running tests TeamCity: In Edit Configuration Settings -> Build Step -> SBT Commands enter the following
    "project identity-tests" clean compile "test-only -- -l unstable"
    to add further tags use this format "test-only -- -l Unstable -l OptionalTest"
    NOTE: Ideally you should not use -n as this will ignore tests that are untagged. By only using -l these untagged
    tests will also be run
  */

  //test intermittently fails, e.g. tests running against 3rd party sites are more reliant on stable connections
  object Unstable extends Tag("Unstable")

  //test is essential and the site will not function without this feature
  object CoreTest extends Tag("CoreTest")

  //test is important but the site can handle this feature missing for a short time
  object OptionalTest extends Tag("OptionalTest")

