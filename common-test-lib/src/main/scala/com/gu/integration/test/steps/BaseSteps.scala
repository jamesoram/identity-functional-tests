package com.gu.integration.test.steps

import com.gu.automation.support.TestLogging
import com.gu.integration.test.pages.common.{ParentPage, TermsOfServicePage}
import com.gu.integration.test.util.ElementLoader
import com.gu.integration.test.util.PageLoader._
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers

case class BaseSteps(implicit driver: WebDriver) extends TestLogging with Matchers {
  def goToStartPage(useBetaRedirect: Boolean = false, subPath: Option[String] = None): ParentPage = {
    val fullFrontBaseUrl = frontsBaseUrl + subPath.getOrElse("")
    logger.step(s"I am on base page at url: $fullFrontBaseUrl")
    lazy val parentPage = new ParentPage()
    goTo(parentPage, fullFrontBaseUrl, useBetaRedirect)
  }

  def goToTermsOfServicePage(useBetaRedirect: Boolean = false): TermsOfServicePage = {
    goToStartPage()
    Thread.sleep(500)
    //    lazy val tosPage = new TermsOfServicePage()
    //    goTo(tosPage, fromRelativeUrl("/../help/terms-of-service"))
    ElementLoader.findByDataLinkAttribute("terms").click()
    logger.step(s"I am on the Terms of Service page at url: " + driver.getCurrentUrl)
    new TermsOfServicePage()
  }
}
