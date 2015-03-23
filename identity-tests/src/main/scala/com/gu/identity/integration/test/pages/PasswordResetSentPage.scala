package com.gu.identity.integration.test.pages

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}

class PasswordResetSentPage(implicit driver: WebDriver) extends ContainerWithSigninModulePage {
  private def message: WebElement = {
    val xpath = "//h1[@class='identity-title']"
    val wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }

 def getMessagText(): String = {
  message.getText
 }

}