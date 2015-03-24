package com.gu.integration.test.steps

import com.gu.automation.support.{LocalStorageManager, Config, TestLogging}
import com.gu.identity.integration.test.pages.{FrontPage, RegisterPage, FacebookParentPage}
import com.gu.identity.integration.test.util.facebook.{FacebookTestUserService, AccessToken, FacebookTestUser}
import com.gu.integration.test.util.PageLoader._
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.Matchers

case class SocialNetworkSteps(implicit driver: WebDriver) extends TestLogging with Matchers {

  private val facebookLocalStorageKey = "gu.id.nextFbCheck"

  def accessToken(): AccessToken = AccessToken(Config().getUserValue("facebookApplicationId"), Config().getUserValue("facebookApplicationSecret"))

  def createNewFacebookTestUser(): FacebookTestUser = {
    logger.step(s"Creating new Facebook user")
    val facebookUser = FacebookTestUserService.createUser(new FacebookTestUser("Test User"), accessToken())
    logger.step(s"Created new Facebook user ${facebookUser.email.get} with Id ${facebookUser.id.get}")
    facebookUser
  }

  def deleteFacebookTestUser(facebookTestUser: FacebookTestUser): Boolean = {
    logger.step(s"Deleting Facebook test user ${facebookTestUser.email.get} with Id ${facebookTestUser.id.get}")
    FacebookTestUserService.deleteUser(facebookTestUser, accessToken())
  }

  def goToFacebookAsUser(facebookTestUser: FacebookTestUser): FacebookParentPage = {
    logger.step(s"I am on the default Facebook landing page after login as ${facebookTestUser.email.get}")
    lazy val facebookSignInPage = new FacebookParentPage()
    goTo(facebookSignInPage, "https://facebook.com")
    facebookSignInPage.enterEmail(facebookTestUser.email.get)
    facebookSignInPage.enterPassword(facebookTestUser.password.get)
    facebookSignInPage.clickLoginButton()

    facebookSignInPage
  }

  def checkUserGotFacebookEmailError(registerPage: RegisterPage) = {
    registerPage.getFormErrorText() match {
      case Some(errorMessage: String) =>
      case None => fail("Did not get Facebook e-mail error message")
    }
  }

  def checkUserGotAutoSignInBanner(frontPage: FrontPage) = {
    frontPage.getSiteMessageText() match {
      case Some(text: String) => text should include ("signed into the Guardian using Facebook")
      case _ => fail("Did not get auto sign in banner")
    }
  }

  def clearLocalStorageFacebookValue() = {
    LocalStorageManager.remove(facebookLocalStorageKey)
  }

}
