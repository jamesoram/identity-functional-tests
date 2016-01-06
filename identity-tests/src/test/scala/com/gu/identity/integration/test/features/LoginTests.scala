package com.gu.identity.integration.test.features

import com.gu.automation.support.Config
import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.steps.SignInSteps
import com.gu.identity.integration.test.tags.{SmokeTest, SocialTest, OptionalTest, CoreTest}
import com.gu.integration.test.steps.BaseSteps
import com.gu.integration.test.util.UserConfig._
import org.openqa.selenium.WebDriver

class LoginTests extends IdentitySeleniumTestSuite {

  feature("Login feature") {
    scenarioWeb("L1: should be able to login using credentials", CoreTest, SmokeTest) { implicit driver: WebDriver =>
      BaseSteps().goToStartPage()
      SignInSteps().signIn()
      SignInSteps().checkUserIsLoggedIn(get("loginName"))
      SignInSteps().checkUserIsLoggedInSecurely()
    }

    scenarioWeb("L2: should be able to login using existing Facebook account", OptionalTest, SocialTest) {
      implicit driver: WebDriver =>
      BaseSteps().goToStartPage()
      SignInSteps().signInUsingFaceBook()
      SignInSteps().checkUserIsLoggedIn(Config().getUserValue("faceBookLoginName"))
      SignInSteps().checkUserIsLoggedInSecurely()
    }

    scenarioWeb("L3: should be able to login using existing Google account", OptionalTest, SocialTest) {
      implicit driver: WebDriver =>
      BaseSteps().goToStartPage()
      SignInSteps().signInUsingGoogle()
      SignInSteps().checkUserIsLoggedIn(Config().getUserValue("googleLoginName"))
      SignInSteps().checkUserIsLoggedInSecurely()
    }
  }
}
