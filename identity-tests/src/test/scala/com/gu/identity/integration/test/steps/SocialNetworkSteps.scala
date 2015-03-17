package com.gu.integration.test.steps

import com.gu.automation.support.{Config, TestLogging}
import com.gu.identity.integration.test.pages.{FacebookParentPage}
import com.gu.identity.integration.test.util.facebook.{FacebookTestUserService, AccessToken, FacebookTestUser}
import com.gu.integration.test.util.PageLoader._
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers

case class SocialNetworkSteps(implicit driver: WebDriver) extends TestLogging with Matchers {

  def accessToken(): AccessToken = AccessToken(Config().getUserValue("facebookApplicationId"), Config().getUserValue("facebookApplicationSecret"))

  def createNewFacebookTestUser(): FacebookTestUser = {
    logger.step(s"Creating new Facebook user")
    val facebookUser = FacebookTestUserService.createUser(new FacebookTestUser("Test User"), accessToken())
    logger.step(s"Created new Facebook user ${facebookUser.email} with Id ${facebookUser.id}")
    facebookUser
  }

  def deleteFacebookTestUser(facebookTestUser: FacebookTestUser): Boolean = {
    logger.step(s"Deleting Facebook test user ${facebookTestUser.email} with Id ${facebookTestUser.id}")
    FacebookTestUserService.deleteUser(facebookTestUser, accessToken())
  }

  def goToFacebookAsUser(facebookTestUser: FacebookTestUser): FacebookParentPage = {
    logger.step(s"I am on the default Facebook landing page after login as ${facebookTestUser.email}")
    lazy val facebookSignInPage = new FacebookParentPage()
    goTo(facebookSignInPage, "https://facebook.com")
    facebookSignInPage.enterEmail(facebookTestUser.email.get)
    facebookSignInPage.enterPassword(facebookTestUser.password.get)
    facebookSignInPage.loginInButton.click()

    facebookSignInPage
  }

}
