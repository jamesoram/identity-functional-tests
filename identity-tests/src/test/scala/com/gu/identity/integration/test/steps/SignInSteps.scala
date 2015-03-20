package com.gu.identity.integration.test.steps

import com.gu.automation.support.{Config, TestLogging}
import com.gu.identity.integration.test.pages.{ContainerWithSigninModulePage, SignInPage}
import com.gu.identity.integration.test.util.User
import com.gu.integration.test.steps.BaseSteps
import com.gu.integration.test.util.CookieUtil._
import com.gu.integration.test.util.UserConfig._
import org.openqa.selenium.{Cookie, WebDriver}
import org.scalatest.Matchers

/**
 * Steps for when the user is logging in and checking that he has been successfully logged in
 */
case class SignInSteps(implicit driver: WebDriver) extends TestLogging with Matchers {
  private val LoginCookie: String = "GU_U"
  private val SecureLoginCookie: String = "SC_GU_U"

  def clickSignInLink(): SignInPage = {
    logger.step("Clicking sign in link")
    new ContainerWithSigninModulePage().signInModule().clickSignInLink()
  }

  def signIn() = {
    signInWith(Config().getLoginEmail(), Config().getLoginPassword())
  }

  def signInWith(email:String, pwd:String) = {
    logger.step("Signing in using credentials")
    val signInPage = SignInSteps().clickSignInLink()
    signInPage.enterEmail(email)
    signInPage.enterPwd(pwd)
    signInPage.signInButton.click()
  }

  def checkUserIsLoggedIn(user: User): ContainerWithSigninModulePage = {
    logger.step(s"Signing in using api")
    BaseSteps().goToStartPage(useBetaRedirect = false)
    checkUserIsLoggedIn(user.userName)
    new ContainerWithSigninModulePage()
  }

  def checkUserIsLoggedIn(expectedLoginName: String) = {
    logger.step(s"Checking that user is logged in")
    val loginName = new ContainerWithSigninModulePage().signInModule().signInName.getText
    loginName.contains(expectedLoginName) should be(true)

    val loginCookie = getCookie(LoginCookie)
    loginCookie.getValue should not be empty
  }

  def checkUserIsLoggedInSecurely() = {
    val secureLoginCookie: Cookie = getSecureCookie(SecureLoginCookie, Some(get("secureEditProfileLink")))

    secureLoginCookie.getValue should not be empty
  }

  def signInUsingFaceBook() = {
    logger.step(s"Signing in using FaceBook")
    val signInPage = SignInSteps().clickSignInLink()
    val faceBookSignInPage = signInPage.clickFaceBookSignInButton()
    faceBookSignInPage.enterEmail(Config().getUserValue("faceBookEmail"))
    faceBookSignInPage.enterPwd(Config().getUserValue("faceBookPwd"))
    faceBookSignInPage.loginInButton.click()
  }

  def signInUsingNewFaceBook() = {
    logger.step(s"Signing in using FaceBook")
    val signInPage = SignInSteps().clickSignInLink()
    val faceBookSignInPage = signInPage.switchToNewSignIn().clickFaceBookSignInButton()
    faceBookSignInPage.enterEmail(Config().getUserValue("faceBookEmail"))
    faceBookSignInPage.enterPwd(Config().getUserValue("faceBookPwd"))
    faceBookSignInPage.loginInButton.click()
  }

  def signInUsingGoogle() = {
    logger.step(s"Signing in using Google")
    val signInPage = SignInSteps().clickSignInLink()
    val googleSignInPage = signInPage.switchToNewSignIn().clickGoogleSignInButton()
    googleSignInPage.enterEmail(Config().getUserValue("googleEmail"))
    googleSignInPage.enterPwd(Config().getUserValue("googlePwd"))
    googleSignInPage.loginInButton.click()
  }

  def signOut(pageWithSignInModule: ContainerWithSigninModulePage) = {
    logger.step("Signing out")
    pageWithSignInModule.signInModule().clickSignInLinkWhenLoggedIn().clickSignOut()
  }

  def checkUserIsNotLoggedIn(expectedLoginName: String) = {
    logger.step(s"Checking that user is not logged in")
    val loginName = new ContainerWithSigninModulePage().signInModule().signInName.getText
    loginName should not be(expectedLoginName)

    val loginCookie = getCookie(LoginCookie)
    loginCookie should be (null)
  }

  def clearLoginCookies() = {
    removeCookie(LoginCookie)
    removeCookie(SecureLoginCookie)
  }

  def checkThatLoginCookieExists() = {
    getCookie(LoginCookie) should not be (null)
  }
}
