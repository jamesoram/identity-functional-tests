package com.gu.identity.integration.test.pages

import com.gu.integration.test.pages.common.ParentPage
import org.openqa.selenium.{By, WebDriver, WebElement}

class FaceBookAuthDialog(implicit driver: WebDriver) extends ParentPage {
  def confirmButton: WebElement = driver.findElement(By.xpath("//button[@name='__CONFIRM__']"))
}
