package com.gu.identity.integration.test.pages

import org.openqa.selenium.WebDriver

class SignInWithJobsPage(implicit driver: WebDriver) extends SignInPage {

  def checkTermsVisible: Boolean = {
    driver.getPageSource.contains("Guardian Jobs")
  }

}
