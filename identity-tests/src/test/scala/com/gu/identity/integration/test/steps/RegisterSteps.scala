package com.gu.identity.integration.test.steps

import com.gu.automation.support.TestLogging
import com.gu.identity.integration.test.pages.RegisterPage
import com.gu.identity.integration.test.util.{FormError, User}
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers

case class RegisterSteps(implicit driver: WebDriver) extends TestLogging with Matchers {

  def registerNewTempUser(registerPage: RegisterPage): Either[List[FormError], User] = {
    logger.step("Creating a new random user")
    val randomGeneratedUser = User.generateRandomUser()
    registerNewTempUserUsing(registerPage, randomGeneratedUser)
  }

  def registerUserWithUserName(registerPage: RegisterPage, userName:String): Either[List[FormError], User] = {
    logger.step("Creating a new random user")
    val randomGeneratedUser = User.generateRandomUserWith(userName)
    registerNewTempUserUsing(registerPage, randomGeneratedUser)
  }

  def registerNewTempUserUsing(registerPage: RegisterPage, user:User): Either[List[FormError], User] = {
    logger.step("Creating a new user")
    registerPage.enterEmail(user.email)
    enterPasswordIfSet(registerPage, user)
    enterFirstNameIfSet(registerPage, user)
    enterLastNameIfSet(registerPage, user)
    registerPage.enterUsername(user.userName)

    registerPage.clickCreateUser()

    if (registerPage.registrationComplete) { //finding the errors takes time so only do so if the button is not visible
      Right(user)
    } else {
      Left(registerPage.getAllValidationFormErrors)
    }
  }

  private def enterPasswordIfSet(registerPage: RegisterPage, user: User) {
    for (password <- user.pwd)
      registerPage.enterPwd(password)
  }

  private def enterFirstNameIfSet(registerPage: RegisterPage, user: User) {
    for (firstName <- user.firstName)
      registerPage.enterFirstName(firstName)
  }

  private def enterLastNameIfSet(registerPage: RegisterPage, user: User) {
    for (lastName <- user.lastName)
      registerPage.enterLastName(lastName)
  }

}
