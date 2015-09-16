package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.{JavascriptExecutor, By, WebDriver, WebElement}

class GoogleSignInPage(implicit driver: WebDriver) extends ParentPage {
  private def emailInputField: WebElement = driver.findElement(By.id("Email"))
  private def pwdInputField: WebElement = driver.findElement(By.id("Passwd"))
  def loginInButton: WebElement = driver.findElement(By.id("signIn"))
  def nextButton: WebElement = driver.findElement(By.id("next"))

  def enterEmail(email: String) {
    emailInputField.sendKeys(email)
    this
  }

  def enterPwd(pwd: String) {
    pwdInputField.sendKeys(pwd)
    this
  }

  def clickLogInButton = {
    loginInButton.click()
    waitForPageToLoad // workaround to stabilise too rapid selenium actions
    //chrome browser flicks user down to random place in content after signing in with Google+ so scroll back to top
    driver.asInstanceOf[JavascriptExecutor].executeScript("scroll(0, -400)")
  }
}
