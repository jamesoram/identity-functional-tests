package com.gu.identity.integration.test.pages

import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.{WebDriver, WebElement}

trait ThirdPartyConditions {

  private def jobsTC(implicit driver: WebDriver): WebElement = findByTestAttribute("jobs-tc")
  private def jobsPrivacy(implicit driver: WebDriver): WebElement = findByTestAttribute("jobs-privacy")

  def checkJobsTermsVisible(implicit driver: WebDriver): Boolean = {
    jobsTC.isDisplayed && jobsPrivacy.isDisplayed
  }

}
