package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.{WebElement, WebDriver}

class AgreePage(implicit  driver: WebDriver) extends ParentPage with ThirdPartyConditions {
  val continueLink: WebElement = findByDataLinkAttribute("Continue")

  def clickContinue(): FrontPage  = {
    waitUntil(visibilityOf(continueLink), 10)
    continueLink.click()
    new FrontPage()
  }
}
