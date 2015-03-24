package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}

class EditProfilePage(implicit driver: WebDriver) extends ParentPage {
  private def editAccountDetailsTab: WebElement = findByTestAttribute("edit-account-details")
  private def confirmWithFacebookButton: WebElement = findByTestAttribute("facebook-sign-in")
  private def confirmWithGoogleButton: WebElement = findByTestAttribute("google-sign-in")

  private def pageHeader: Option[WebElement] = {
    try {
      val cssSelector = ".identity-title"
      val wait = new WebDriverWait(driver, 15)
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)))
      Some(driver.findElement(By.cssSelector(cssSelector)))
    } catch {
      case e: Throwable => None
    }
  }

  def clickEditAccountDetailsTab() = {
    waitUntil(visibilityOf(editAccountDetailsTab))
    editAccountDetailsTab.click()
    new EditAccountDetailsModule()
  }

  def getHeaderText(): Option[String] = {
    pageHeader match {
      case Some(element: WebElement) => Some(element.getText())
      case None => None
    }
  }

  def clickConfirmWithFacebookButton = {
    confirmWithFacebookButton.click()
    new FacebookConfirmPasswordDialog()
  }

  def clickConfirmWithGoogleButton = {
    confirmWithGoogleButton.click()
    new GoogleConfirmPasswordDialog()
  }
}
