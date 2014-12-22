package com.gu.integration.test.config

import java.util.concurrent.TimeUnit.SECONDS

import com.gu.automation.support.TestLogging
import org.openqa.selenium.WebDriver

object WebdriverInitialiser extends TestLogging {

  def augmentWebDriver(implicit webDriver: WebDriver): WebDriver = {
    webDriver.manage().timeouts().implicitlyWait(15, SECONDS)
    webDriver
  }
}
