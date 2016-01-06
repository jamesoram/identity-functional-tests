package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.support.ui.{WebDriverWait, ExpectedConditions}
import org.openqa.selenium.{By, WebDriver, WebElement}

class GoogleConfirmPasswordDialog(implicit driver: WebDriver) extends ParentPage {
  private def chooseAccountButton: WebElement = {
    driver.findElement(By.cssSelector("#choose-account-0"))
  }

  def clickChooseAccountButton() = {
    chooseAccountButton.click()
    new EditProfilePage()
  }
}
