package com.gu.identity.integration.test.features

import com.gu.identity.integration.test.IdentitySeleniumTestSuite
import com.gu.identity.integration.test.steps.TermsAndConditionsSteps
import com.gu.identity.integration.test.tags.CoreTest
import org.openqa.selenium.WebDriver

class TermsAndConditionsTests extends IdentitySeleniumTestSuite {


  feature("T&C's feature") {
    scenarioWeb("should be to agree with Jobs T&C's when logging-in from jobs", CoreTest) { implicit driver: WebDriver =>
      val signinPage = TermsAndConditionsSteps().goToJobsSite.clickLogin()
      TermsAndConditionsSteps().checkTermsVisible(signinPage)
    }
  }

}
