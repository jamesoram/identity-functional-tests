package com.gu.identity.integration.test.pages

import com.gu.automation.support.Config
import com.gu.integration.test.pages.common.ParentPage
import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.{By, WebDriver, WebElement}

class ProfileNavMenu(implicit driver: WebDriver) extends ParentPage {
  private def menuList: WebElement = findByTestAttribute("nav-popup-profile")
  private def menuElement(dataLinkName: String): By = By.cssSelector(s"a[data-link-name='$dataLinkName']")

  def clickEditProfile(): EditProfilePage = {
    clickMenuElement("Edit profile")
    new EditProfilePage()
  }

  def clickChangePassword(): ChangePasswordPage = {
//    clickMenuElement("Change password")
    // again, this is hardcoded in NGW
    driver.get(Config().getUserValue("changePassword"))
    new ChangePasswordPage()
  }

  def clickSignOut(): ContainerWithSigninModulePage = {
//    clickMenuElement("Sign out")
//    waitForPageToLoad
//    lazy val pageWithSignIn = new ContainerWithSigninModulePage()
//    goTo(pageWithSignIn, frontsBaseUrl, useBetaRedirect = false)
    // hardcoded in NGW
    driver.get(Config().getUserValue("signOut"))
    driver.get(Config().getTestBaseUrl())
    new ContainerWithSigninModulePage()
  }

  def clickMenuElement(dataLinkName: String) = {
//    waitUntil(visibilityOf(menuList))
//    driver.findElement(menuElement(dataLinkName)).click()
    // NGW have all their nav links hardcoded to production...
    driver.get(Config().getUserValue("secureEditProfileLink"))
  }
}