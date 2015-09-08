package com.gu.integration.test.pages.common

import com.gu.automation.support.TestLogging
import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}

/**
 * This is a parent class for all Page Objects and does both pull in some traits, most importantly the PageHelper, so that
 * not all concrete page objects need to do it explicitly. And also contains elements present on all pages.
 */
class ParentPage(implicit driver: WebDriver) extends TestLogging {
  def waitForPageToLoad = {  //removes some test instability by forcing the page to load first
    val wait = new WebDriverWait(driver, 10)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("html")))
  }
}