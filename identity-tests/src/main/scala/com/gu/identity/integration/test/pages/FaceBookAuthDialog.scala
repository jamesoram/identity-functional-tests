package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}

class FaceBookAuthDialog(implicit driver: WebDriver) extends ParentPage {

  private def confirmButton: WebElement = {
    val xpath = "id('platformDialogForm')//button[@name='__CONFIRM__']"
    val wait = new WebDriverWait(driver, 10)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }

  private def editInformationProvided: WebElement = {
    val id = "u_0_l"
    val wait = new WebDriverWait(driver, 10)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
    driver.findElement(By.id(id))
  }

  private def emailCheckBox: WebElement = {
    val xpath = "(//span[preceding::input[@value='email']])[1]"
    val wait = new WebDriverWait(driver, 20)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    driver.findElement(By.xpath(xpath))
  }

  def clickConfirmButton(email: String, pwd: String) = {
    userSignedInToFacebook(email, pwd) //workaround
    confirmButton.click()
    new FrontPage()
  }

  def clickEditInformationProvided(email: String, pwd: String) = {
    userSignedInToFacebook(email, pwd) //workaround
    editInformationProvided.click()
    this
  }

  def clickEmailCheckBox() = {
    emailCheckBox.click()
    this
  }

  def userSignedInToFacebook(email: String, pwd: String) = {
    //Work around for tests where facebook sign in is not remembered
    waitForPageToLoad
    if (driver.getCurrentUrl contains "facebook.com/login.php") {
      driver.findElement(By.id("email")).sendKeys(email)
      driver.findElement(By.id("pass")).sendKeys(pwd)
      driver.findElement(By.id("u_0_2")).click()
    }
  }
}
