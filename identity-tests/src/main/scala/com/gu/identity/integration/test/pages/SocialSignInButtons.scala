package com.gu.identity.integration.test.pages

import com.gu.integration.test.util.ElementLoader._
import org.openqa.selenium.{WebDriver, WebElement}

trait SocialSignInButtons {

  private def faceBookSignInButton(implicit driver: WebDriver): WebElement = findByTestAttribute("facebook-sign-in")
  private def googleSignInButton(implicit driver: WebDriver): WebElement = findByTestAttribute("google-sign-in")

  def getFaceBookSignInLink(implicit driver: WebDriver): String = {
    faceBookSignInButton.getAttribute("href")
  }

  def clickResignInWithFacebook(implicit driver: WebDriver) = {
    faceBookSignInButton.click()
    this
  }

  def clickGoogleSignInButton(implicit driver: WebDriver): GoogleSignInPage = {
    googleSignInButton.click()
    new GoogleSignInPage()
  }

  def getGoogleSignInLink(implicit driver: WebDriver): String = {
    googleSignInButton.getAttribute("href")
  }

}
