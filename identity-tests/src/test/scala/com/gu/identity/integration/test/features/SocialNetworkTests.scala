package com.gu.identity.integration.test.features

import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.pages.{FrontPage, ContainerWithSigninModulePage}
import com.gu.identity.integration.test.steps.{SignInSteps, UserSteps}
import com.gu.integration.test.steps.{BaseSteps, SocialNetworkSteps}
import org.openqa.selenium.WebDriver


class SocialNetworkTests extends IdentitySeleniumTestSuite {

  feature("Registration and sign-in using Facebook") {
    scenarioWeb("should be able to register using Facebook") { implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)
      BaseSteps().goToStartPage()
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
      authDialog.clickConfirmButton()
      SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
      SignInSteps().checkThatLoginCookieExists()
      SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    }

    scenarioWeb("should get an error message if e-mail permissions are missing") { implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)
      BaseSteps().goToStartPage()
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
      authDialog.clickEditInformationProvided().clickEmailCheckBox().clickConfirmButton()
      SocialNetworkSteps().checkUserGotFacebookEmailError(registerPage)
      SignInSteps().checkUserIsNotLoggedIn(facebookUser.fullName)
      SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    }

    scenarioWeb("should be auto signed in if registered and logged into Facebook") { implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)
      val frontPage = new FrontPage()
      BaseSteps().goToStartPage()
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
      authDialog.clickConfirmButton()
      SignInSteps().signOut(frontPage)
      val newLogOutTime = System.currentTimeMillis / 1000 - 24 * 3600
      SignInSteps().setSignOutCookieWithTime(newLogOutTime)
      SocialNetworkSteps().clearLocalStorageFacebookValue()
      BaseSteps().goToStartPage()
      SocialNetworkSteps().checkUserGotAutoSignInBanner(frontPage)
      SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
      SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    }

    scenarioWeb("should be asked to re-authenticate when editing profile after logging in with Facebook") { implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)
      BaseSteps().goToStartPage()
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
      authDialog.clickConfirmButton()
      val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
      SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
      val facebookConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithFacebookButton
      val editProfilePage = facebookConfirmPasswordDialog.enterPassword(facebookUser.password.get).clickContinueButton()
      SocialNetworkSteps().checkUserIsOnEditProfilePage(editProfilePage)
      SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    }

    scenarioWeb("should stay on Facebook when entering wrong Facebook password during re-authentication") { implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)
      BaseSteps().goToStartPage()
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
      authDialog.clickConfirmButton()
      val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
      SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
      val facebookConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithFacebookButton
      val editProfilePage = facebookConfirmPasswordDialog.enterPassword("wrongpassword").clickContinueButton()
      SocialNetworkSteps().checkUserIsOnFacebook()
      SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    }

    scenarioWeb("should be asked to re-authenticate when editing profile after logging in with Google") { implicit driver: WebDriver =>
      BaseSteps().goToStartPage()
      SignInSteps().signInUsingGoogle()
      val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
      SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
      val googleConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithGoogleButton
      val editProfilePage = googleConfirmPasswordDialog.clickChooseAccountButton()
      SocialNetworkSteps().checkUserIsOnEditProfilePage(editProfilePage)
    }

    scenarioWeb("Social user can change email on profile and still sign in") { implicit driver: WebDriver =>
      val newEmail = "sp017@changed.com"
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)

      BaseSteps().goToStartPage()
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton().clickConfirmButton()
      val editAccountDetailsPage = UserSteps().goToEditAccountPage(new ContainerWithSigninModulePage()).clickConfirmWithFacebookButton
        .enterPassword(facebookUser.password.get).clickContinueButton().clickEditAccountDetailsTab()
      val originalEmail = editAccountDetailsPage.getEmailAddress()
      editAccountDetailsPage.enterEmailAddress(newEmail)
      editAccountDetailsPage.saveChanges()
      SignInSteps().signOut(new ContainerWithSigninModulePage())
      BaseSteps().goToStartPage()
      SignInSteps().clickSignInLink()
      SignInSteps().clickSignInWithFacebook()
      BaseSteps().goToStartPage()
      SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
    }

  }
}
