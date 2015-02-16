package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.{JavascriptExecutor, WebDriver, WebElement}

/**
 * Do not confuse this with the sign in page. This is only the module which sits at the top of most frontend pages
 */
class SignInModule(implicit driver: WebDriver) extends ParentPage {
  private def signInLink: WebElement = findByTestAttribute("sign-in-link")
  def signInName: WebElement = findByTestAttribute("sign-in-name")

  def clickSignInLink(): SignInPage = {
    new Actions(driver).moveToElement(signInLink).perform()
    signInLink.click()
    new SignInPage()
  }

  def clickSignInLinkWhenLoggedIn(): ProfileNavMenu = {
    val y = signInLink.getLocation
    driver.asInstanceOf[JavascriptExecutor].executeScript(s"window.scrollTo(0, $y)")
    signInLink.click()
    new ProfileNavMenu
  }
}