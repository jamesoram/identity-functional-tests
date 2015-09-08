package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.{StaleElementReferenceException, WebDriver, WebElement}

/**
 * Do not confuse this with the sign in page. This is only the module which sits at the top of most frontend pages
 */
class SignInModule(implicit driver: WebDriver) extends ParentPage {
  private def signInLink: WebElement = findByTestAttribute("sign-in-link")

  def signInName: WebElement = findByTestAttribute("sign-in-name")

  def getSignInName: String = {
    var count: Int = 0
    var name: String = ""
    while (count < 4) {
      try {
        name = signInName.getText
        count = 4
      } catch {
        case e: StaleElementReferenceException => logger.error("stale element: " + "sign-in-link" + " for sign in module")
      }
      count = count + 1
    }
    name
  }

  def clickSignInLink(): SignInPage = {
    new Actions(driver).moveToElement(signInLink).perform()
    waitForPageToLoad
    var count: Int = 0
    while (count < 4) {
      try {
        signInLink.click()
        count = 4
      } catch {
        case e: StaleElementReferenceException => logger.error("stale element: " + "sign-in-link" + " for sign in module")
      }
      count = count + 1
    }
    waitForPageToLoad
    new SignInPage()
  }

  def clickSignInLinkWhenLoggedIn(): ProfileNavMenu = {
    //workaround for stale element caused by jittery javascript
    waitForPageToLoad
    var count: Int = 0
    while (count < 4) {
      try {
        signInLink.click()
        count = 4
      } catch {
        case e: StaleElementReferenceException => logger.error("stale element: " + "sign-in-link" + " for sign in module")
      }
      count = count + 1
    }
    waitForPageToLoad
    new ProfileNavMenu
  }
}