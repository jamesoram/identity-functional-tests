package com.gu.identity.integration.test.features

import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.pages.{ContainerWithSigninModulePage, FaceBookAuthDialog, FrontPage, SignInPage}
import com.gu.identity.integration.test.steps.{SignInSteps, UserSteps}
import com.gu.identity.integration.test.tags.{CoreTest, OptionalTest}
import com.gu.identity.integration.test.util.facebook.FacebookTestUser
import com.gu.integration.test.steps.{BaseSteps, SocialNetworkSteps}
import org.openqa.selenium.WebDriver
import org.scalatest.Tag


class SocialNetworkTests extends IdentitySeleniumTestSuite {
  //Extends scenarioWeb to include setup/tear down of the test facebook user as there is a hard limit on users allowed
  //BeforeAndAfter was not used as does not work with scenarioWeb
  protected def scenarioFacebook(specText: String, testTags: Tag*)(testFunction: WebDriver => FacebookTestUser => Any) {
    scenarioWeb(specText, testTags: _*)({ implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      try testFunction(driver)(facebookUser)
      finally SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    })
  }

  feature("Registration and sign-in using Facebook") {
    scenarioFacebook("should be able to register using Facebook", OptionalTest) {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.clickRegisterWithFacebookButton()
        authDialog.clickConfirmButton()
        SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
        SignInSteps().checkThatLoginCookieExists()
    }

    scenarioFacebook("should be auto signed in if registered and logged into Facebook", CoreTest) {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        val frontPage = new FrontPage()
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.clickRegisterWithFacebookButton()
        authDialog.clickConfirmButton()
        SignInSteps().signOut(frontPage)
        val newLogOutTime = System.currentTimeMillis / 1000 - 24 * 3600
        SignInSteps().setSignOutCookieWithTime(newLogOutTime)
        SocialNetworkSteps().clearLocalStorageFacebookValue()
        BaseSteps().goToStartPage()
        SocialNetworkSteps().checkUserGotAutoSignInBanner(frontPage)
        SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
    }

    scenarioFacebook("should be asked to re-authenticate when editing profile after logging in with Facebook", OptionalTest) {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.clickRegisterWithFacebookButton()
        authDialog.clickConfirmButton()
        val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
        SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
        val facebookConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithFacebookButton
        val editProfilePage = facebookConfirmPasswordDialog.enterPassword(facebookUser.password.get).clickContinueButton()
        SocialNetworkSteps().checkUserIsOnEditProfilePage(editProfilePage)
    }

    scenarioFacebook("should stay on Facebook when entering wrong Facebook password during re-authentication", OptionalTest) {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.clickRegisterWithFacebookButton()
        authDialog.clickConfirmButton()
        val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
        SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
        val facebookConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithFacebookButton
        facebookConfirmPasswordDialog.enterPassword("wrongpassword").clickContinueButton()
        SocialNetworkSteps().checkUserIsOnFacebook()
    }

  }
  feature("Registration and sign-in using Google") {

    scenarioWeb("should be asked to re-authenticate when editing profile after logging in with Google", OptionalTest) {
      implicit driver: WebDriver =>
        BaseSteps().goToStartPage()
        SignInSteps().signInUsingGoogle()
        val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
        SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
        val googleConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithGoogleButton
        val editProfilePage = googleConfirmPasswordDialog.clickChooseAccountButton()
        SocialNetworkSteps().checkUserIsOnEditProfilePage(editProfilePage)
    }
  }
}
