package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.{WebElement, WebDriver}
import com.gu.integration.test.util.ElementLoader._

class JobsStubPage(implicit  driver: WebDriver) extends ParentPage {

  val loginLink: WebElement = findByDataLinkAttribute("Signin")

  def clickLogin(): SignInPage = {
    loginLink.click()
    new SignInPage
  }

}
