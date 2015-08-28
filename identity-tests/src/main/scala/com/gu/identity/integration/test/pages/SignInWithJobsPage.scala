package com.gu.identity.integration.test.pages

import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.{WebElement, WebDriver}

class SignInWithJobsPage(implicit driver: WebDriver) extends SignInPage {

  private def jobsTC: WebElement = findByTestAttribute("jobs-tc")
  private def jobsPrivacy: WebElement = findByTestAttribute("jobs-privacy")

  def checkTermsVisible: Boolean = {
    jobsTC.isDisplayed && jobsPrivacy.isDisplayed
  }

}
