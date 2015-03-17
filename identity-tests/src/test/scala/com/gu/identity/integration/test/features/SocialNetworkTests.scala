package com.gu.identity.integration.test.features

import com.gu.automation.support.Config
import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.steps.SignInSteps
import com.gu.integration.test.steps.{SocialNetworkSteps, BaseSteps}
import com.gu.integration.test.util.UserConfig._
import com.gu.identity.integration.test.util.facebook._
import org.openqa.selenium.WebDriver

class SocialNetworkTests extends IdentitySeleniumTestSuite {

  feature("Registration and sign-in using Facebook") {
    scenarioWeb("should be able to register using Facebook") { implicit driver: WebDriver =>
      val facebookUser = SocialNetworkSteps().createNewFacebookTestUser()
      SocialNetworkSteps().goToFacebookAsUser(facebookUser)
      BaseSteps().goToStartPage(useBetaRedirect = false)
      val registerPage = SignInSteps().clickSignInLink().clickRegisterNewUserLink()
      registerPage.switchToNewSignIn().clickRegisterWithFacebookButton()
      Thread sleep 10000
      SocialNetworkSteps().deleteFacebookTestUser(facebookUser)
    }

  }
}
