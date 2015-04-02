package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.{JavascriptExecutor, By, WebDriver, WebElement}

class SignInPage(implicit driver: WebDriver) extends ParentPage {

  private def emailInputField: WebElement = findByTestAttribute("signin-email")
  private def pwdInputField: WebElement = findByTestAttribute("signin-pwd")
  def signInButton: WebElement = findByTestAttribute("sign-in-button")
  private def faceBookSignInButton: WebElement = findByTestAttribute("facebook-sign-in")
  private def googleSignInButton: WebElement = findByTestAttribute("google-sign-in")
  private def registerLink: WebElement = findByTestAttribute("register-link")
  private def faceBookEmailElement: WebElement = driver.findElement(By.name("email"))

  def enterEmail(email: String) = {
    emailInputField.sendKeys(email)
    this
  }

  def enterPwd(pwd: String) = {
    pwdInputField.sendKeys(pwd)
    this
  }

  def clickFaceBookSignInButton(): FaceBookSignInPage = {
    ensureOauthActive()
    faceBookSignInButton.click()

    //this is needed because sometimes the above click does not wait for the facebook page to be loaded
    waitUntil(visibilityOf(faceBookEmailElement), 10)

    new FaceBookSignInPage()
  }

  def clickGoogleSignInButton(): GoogleSignInPage = {
    ensureOauthActive()
    googleSignInButton.click()
    new GoogleSignInPage()
  }

  def ensureOauthActive() = {
    if (driver.getCurrentUrl().contains("code")) {
      // with the move to the centralised configuration we need to check that we are testing the correct API
      val oauthActive = driver.asInstanceOf[JavascriptExecutor].executeScript("return guardian.config.switches.idSocialOauth")
      if (!oauthActive.asInstanceOf[Boolean]) {
        throw new RuntimeException("Federation API not active")
      }
    }
  }

  def clickRegisterNewUserLink(): RegisterPage = {
    registerLink.click()
    new RegisterPage()
  }
}
