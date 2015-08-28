package com.gu.identity.integration.test.pages

import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.{WebElement, WebDriver}

class SigninWithJobsPage(implicit driver: WebDriver) extends SignInPage {

  val jobsTCs: WebElement = findByDataLinkAttribute("Jobs Terms of Service")
  val jobsPrivacyPolicy: WebElement = findByDataLinkAttribute("Jobs Privacy Policy")

  def termsAndConditionsVisible: Boolean = {
    jobsTCs.isDisplayed && jobsPrivacyPolicy.isDisplayed
  }

}
