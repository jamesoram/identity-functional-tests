package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.support.ui.{WebDriverWait, ExpectedConditions}
import org.openqa.selenium.{By, WebDriver, WebElement}

class FaceBookAuthDialog(implicit driver: WebDriver) extends ParentPage {
  def confirmButton: WebElement = {
    val xpath = "//button[@name='__CONFIRM__']"
    val wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }

  def editInformationProvided: WebElement = {
    val xpath = "//a[text()='Edit the info you provide']"
    val wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }

  def emailCheckBox: WebElement = {
    val xpath = "(//span[preceding::input[@value='email']])[1]"
    val wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }
}
