package com.gu.identity.integration.test.steps

import com.gu.automation.support.TestLogging
import com.gu.identity.integration.test.pages.JobsStubPage
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers

case class TermsAndConditionsSteps(implicit driver: WebDriver) extends TestLogging with Matchers {

  val jobsStub = "http://public-jobs-thegulocal-com.s3-website-eu-west-1.amazonaws.com"

  def goToJobsSite: JobsStubPage = {
    logger.step(s"I am on stub jobs page at url: $jobsStub")
    driver.navigate().to(jobsStub)
    // Wait for javascript to execute
    Thread.sleep(500)
    new JobsStubPage()
  }

}
