package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.{WebElement, WebDriver}
import com.gu.integration.test.util.ElementLoader._

class JobsStubPage(implicit  driver: WebDriver) extends ParentPage {

  val loginLink: WebElement = findByDataLinkAttribute("Signin")
  val registrationLink: WebElement = findByDataLinkAttribute("Register")

  def clickLogin(): SignInPage = {
    waitUntil(visibilityOf(loginLink), 10)
    loginLink.click()
    new SignInPage
  }

  def clickRegister(): RegisterPage = {
    waitUntil(visibilityOf(loginLink), 10)
    registrationLink.click()
    new RegisterPage
  }

  def clickLoginAsExistingUser(): AgreePage = {
    waitUntil(visibilityOf(loginLink), 10)
    loginLink.click()
    new AgreePage
  }

  def clickLoginAsApprovedUser(): FrontPage = {
    waitUntil(visibilityOf(loginLink), 10)
    loginLink.click()
    new FrontPage
  }

}
