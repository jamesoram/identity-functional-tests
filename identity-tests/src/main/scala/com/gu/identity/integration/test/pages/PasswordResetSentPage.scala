package com.gu.identity.integration.test.pages

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}

class PasswordResetSentPage(implicit driver: WebDriver) extends ContainerWithSigninModulePage {
  private def message: WebElement = {
    driver.findElement(By.cssSelector(".identity-title"))
  }

 def getMessageText(): String = {
  message.getText
 }

}