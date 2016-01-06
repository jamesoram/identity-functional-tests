package com.gu.identity.integration.test.pages

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{WebElement, By, WebDriver}

class FrontPage(implicit driver: WebDriver) extends ContainerWithSigninModulePage {

  private def getSiteMessage: Option[WebElement] =  {
    try {
      val xpath = "//p[@class='site-message__message']"
      val wait = new WebDriverWait(driver, 15)
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)))
      Some(driver.findElement(By.xpath(xpath)))
    } catch {
      case _: Throwable => None
    }
  }


  def getSiteMessageText: Option[String] =
  {
    getSiteMessage match {
      case Some(element: WebElement) => Some(element.getText)
      case None => None
    }
  }

}
