package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common._
import org.openqa.selenium.support.ui.{WebDriverWait, ExpectedConditions}
import org.openqa.selenium.{By, WebDriver, WebElement}

class FacebookConfirmPasswordDialog(implicit driver: WebDriver) extends ParentPage {
  private def continueButton: WebElement = {
    val cssSelector = "#u_0_0"
    val wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)))
    driver.findElement(By.cssSelector(cssSelector))
  }

  private def confirmPasswordField: WebElement = {
    val xpath = "//input[@type='password']"
    val wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }

  def clickContinueButton(): EditProfilePage = {
    continueButton.click()
    new EditProfilePage()
  }

  def enterPassword(password: String) = {
    confirmPasswordField.sendKeys(password)
    this
  }
}

