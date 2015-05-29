package com.gu.identity.integration.test.features

import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.pages.{FrontPage, ContainerWithSigninModulePage, FaceBookAuthDialog, SignInPage}
import com.gu.identity.integration.test.steps.{UserSteps, SignInSteps}
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
    scenarioFacebook("should be able to register using Facebook") { implicit driver: WebDriver =>
      implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
        authDialog.clickConfirmButton()
        SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
        SignInSteps().checkThatLoginCookieExists()
    }

    scenarioFacebook("should get an error message if e-mail permissions are missing") { implicit driver: WebDriver =>
      implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
        authDialog.clickEditInformationProvided().clickEmailCheckBox().clickConfirmButton()
        SocialNetworkSteps().checkUserGotFacebookEmailError(registerPage)
        SignInSteps().checkUserIsNotLoggedIn(facebookUser.fullName)
    }

    scenarioFacebook("should be auto signed in if registered and logged into Facebook") { implicit driver: WebDriver =>
      implicit facebookUser: FacebookTestUser =>
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
    }

    scenarioFacebook("should be asked to re-authenticate when editing profile after logging in with Facebook") {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
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
    }

    scenarioFacebook("should stay on Facebook when entering wrong Facebook password during re-authentication") {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
        authDialog.clickConfirmButton()
        val editAccountDetailsPage = UserSteps().goToEditProfilePage(new ContainerWithSigninModulePage())
        SocialNetworkSteps().checkUserGotReAuthenticationMessage(editAccountDetailsPage)
        val facebookConfirmPasswordDialog = editAccountDetailsPage.clickConfirmWithFacebookButton
        facebookConfirmPasswordDialog.enterPassword("wrongpassword").clickContinueButton()
        SocialNetworkSteps().checkUserIsOnFacebook()
    }

    scenarioFacebook("Social user can change email on profile and still sign in") { implicit driver: WebDriver =>
      implicit facebookUser: FacebookTestUser =>
        val newEmail = System.currentTimeMillis() + "changed@changed.com"
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)

        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        registerPage.switchToNewSignIn().clickRegisterWithFacebookButton().clickConfirmButton()
        val editAccountDetailsPage = UserSteps().goToEditAccountPage(new ContainerWithSigninModulePage()).clickConfirmWithFacebookButton
          .enterPassword(facebookUser.password.get).clickContinueButton().clickEditAccountDetailsTab()

        editAccountDetailsPage.enterEmailAddress(newEmail)
        editAccountDetailsPage.saveChanges()
        SignInSteps().signOut(new ContainerWithSigninModulePage())

        BaseSteps().goToStartPage()
        SignInSteps().clickSignInLink()
        SignInSteps().clickSignInWithFacebook()

        BaseSteps().goToStartPage()
        SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
        BaseSteps().goToStartPage()
        val signInEmail = UserSteps().goToEditAccountPage(new ContainerWithSigninModulePage())
          .clickEditAccountDetailsTab().getEmailAddress()

        signInEmail should be(newEmail) //confirms facebook sign in was against correctly changed email
    }

    scenarioFacebook("should be asked to re-request e-mail permissions after denying them the first time") {
      implicit driver: WebDriver => implicit facebookUser: FacebookTestUser =>
        SocialNetworkSteps().goToFacebookAsUser(facebookUser)
        BaseSteps().goToStartPage()
        val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
        val authDialog = registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
        authDialog.clickEditInformationProvided().clickEmailCheckBox().clickConfirmButton()
        SocialNetworkSteps().checkUserGotFacebookEmailError(registerPage)
        SignInSteps().checkUserIsNotLoggedIn(facebookUser.fullName)
        val signinPage = new SignInPage()
        signinPage.clickFaceBookSignInButton(waitForFacebookEmailElement = false)
        val requestPermissionsDialog = new FaceBookAuthDialog()
        requestPermissionsDialog.clickConfirmButton()
        BaseSteps().goToStartPage()
        SignInSteps().checkUserIsLoggedIn(facebookUser.fullName)
    }
  }
  feature("Registration and sign-in using Google") {

    scenarioWeb("should be asked to re-authenticate when editing profile after logging in with Google") {
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
