package com.gu.identity.integration.test.features

import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.tags.Unstable
import com.gu.integration.test.steps.BaseSteps
import org.openqa.selenium.WebDriver

class TermsOfServiceTests extends IdentitySeleniumTestSuite {

  feature("Terms of Service feature") {
    scenarioWeb("should not be empty or trivial", Unstable) { implicit driver: WebDriver =>
      val tosPage = BaseSteps().goToTermsOfServicePage()
      val minimumTosContentSize = 100
      val tosContent: String = tosPage.getContent()

      tosContent.length should be > minimumTosContentSize

      tosContent.contains("Terms and conditions") should be (right = true)
      tosContent.contains("Guardian") should be (right = true)
      tosContent.contains("theguardian.com") should be (right = true)
      tosContent.contains("disclaimer") should be (right = true)
    }
  }
}
