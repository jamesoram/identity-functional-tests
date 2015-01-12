package com.gu.integration.test

import com.gu.automation.core.WebDriverFeatureSpec
import com.gu.automation.support.{Browser, TestRetries}
import com.gu.integration.test.config.WebdriverInitialiser.augmentWebDriver
import org.openqa.selenium.WebDriver
import org.scalatest.{ParallelTestExecution, Matchers}

abstract class SeleniumTestSuite extends WebDriverFeatureSpec with Matchers with TestRetries with ParallelTestExecution {

  override protected def startDriver(testName: String, targetBrowser: Browser): WebDriver = {
    augmentWebDriver(super.startDriver(testName, targetBrowser))
  }
}