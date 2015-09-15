package com.gu.identity.integration.test.pages

import com.gu.integration.test.util.ElementLoader._
import com.gu.integration.test.util.WebElementEnhancer._
import org.openqa.selenium.{StaleElementReferenceException, By, WebDriver, WebElement}

class RegisterPage(implicit driver: WebDriver) extends UserFormPage with ThirdPartyConditions with SocialSignInButtons {
  private def emailInputField: WebElement = findByTestAttribute("reg-email")

  private def userNameInputField: WebElement = findByTestAttribute("reg-username")

  private def firstNameInputField: WebElement = findByTestAttribute("reg-first-name")

  private def lastNameInputField: WebElement = findByTestAttribute("reg-second-name")

  private def pwdInputField: WebElement = findByTestAttribute("reg-pwd")

  private def createButton: WebElement = findByTestAttribute("create-user-button")

  private def registerWithFacebookButton: WebElement = findByTestAttribute("facebook-sign-in")

  private def completeRegistrationButton: WebElement = findByDataLinkAttribute("Continue")

  def enterEmail(email: String) = {
    emailInputField.sendKeys(email)
    this
  }

  def enterPwd(pwd: String) = {
    pwdInputField.sendKeys(pwd)
    this
  }

  def enterUsername(userName: String) = {
    userNameInputField.sendKeys(userName)
    this
  }

  def enterFirstName(firstName: String) = {
    firstNameInputField.sendKeys(firstName)
    this
  }

  def enterLastName(lastName: String) = {
    lastNameInputField.sendKeys(lastName)
    this
  }

  def clickCreateUser() = {
    createButton.scrollIntoView()
    createButton.click()
    this
  }

  def clickRegisterWithFacebookButton(): FaceBookAuthDialog = {
    //workaround for stale element caused by jittery javascript
    waitForPageToLoad
    var count: Int = 0
    while (count < 4) {
      try {
        registerWithFacebookButton.click()
        count = 4
      } catch {
        case e: StaleElementReferenceException => logger.error("stale element: " + "facebook-sign-in" + " for register with Facebook button")
      }
      count = count + 1
    }

    new FaceBookAuthDialog()
  }

  def getFormErrorText: Option[String] = {
    try {
      Some(driver.findElement(By.xpath("//div[@class='form__error']")).getText)
    } catch {
      case _: org.openqa.selenium.NoSuchElementException => None
    }
  }

  def registrationComplete: Boolean = {
    completeRegistrationButton.isDisplayed
  }
}
