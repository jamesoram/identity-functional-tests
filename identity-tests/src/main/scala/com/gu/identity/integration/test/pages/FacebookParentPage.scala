package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.{By, WebDriver, WebElement}

class FacebookParentPage(implicit driver: WebDriver) extends ParentPage {
  private def emailInputField: WebElement = driver.findElement(By.id("email"))
  private def passwordInputField: WebElement = driver.findElement(By.id("pass"))

  private def loginInButton: WebElement = driver.findElement(By.xpath("//input[@value='Log in']"))

  def clickLoginButton() = {
    loginInButton.click()
  }

  def enterEmail(email: String) {
    emailInputField.sendKeys(email)
    this
  }

  def enterPassword(password: String) {
    passwordInputField.sendKeys(password)
    this
  }
}
