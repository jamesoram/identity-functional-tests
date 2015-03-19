package com.gu.identity.integration.test.util.facebook

import com.stackmob.newman._
import com.stackmob.newman.dsl._
import scala.concurrent._
import scala.concurrent.duration._
import net.liftweb.json._
import java.net.{URLEncoder, URL}

class AccessToken(val applicationId: String, applicationSecret: String) {

  lazy val authorisationLocation = "https://graph.facebook.com/oauth/access_token"

  var value: Option[String] = None

  implicit val httpClient = new ApacheHttpClient

  def retrieve(): Unit = {
    if (!value.isDefined) {
      val payload = Map(
        "client_id" -> applicationId,
        "client_secret" -> applicationSecret,
        "grant_type" -> "client_credentials"
      ).map { case (k, v) => s"$k=$v"}.mkString("&")

      val url = new URL(s"$authorisationLocation?$payload")
      val response = Await.result(GET(url).apply, 30.second)

      value = Some(response.bodyString.split("=")(1))
    }
  }

}

object AccessToken {
  def apply(applicationId: String, applicationSecret: String) = new AccessToken(applicationId, applicationSecret)
}

case class FacebookTestUser(
  fullName: String,
  installed: String = "false",
  password: Option[String] = None,
  locale: String = "en_US",
  permissions: String = "read_stream",
  method: String = "post",
  id: Option[String] = None,
  email: Option[String] = None,
  loginUrl: Option[String] = None,
  created: Boolean = false
)

object FacebookTestUserService {

  lazy val graphApiLocation = "https://graph.facebook.com"
  lazy val graphApiVersion = "2.2"

  implicit val httpClient = new ApacheHttpClient
  implicit private val charset = Constants.UTF8Charset
  implicit val formats = DefaultFormats

  def createUser(facebookTestUser: FacebookTestUser, accessToken: AccessToken): FacebookTestUser = {
    accessToken.retrieve()

    val payload = Map(
      "installed" -> facebookTestUser.installed,
      "name" -> facebookTestUser.fullName,
      "locale" -> facebookTestUser.locale,
      "permissions" -> facebookTestUser.permissions,
      "method" -> facebookTestUser.method,
      "access_token"-> accessToken.value.getOrElse("")
    ).map { case (k, v) => s"$k=" + URLEncoder.encode(v.toString)}.mkString("&")

    val url = new URL(s"$graphApiLocation/v$graphApiVersion/${accessToken.applicationId}/accounts/test-users?$payload")
    val response = Await.result(GET(url).apply, 30.second)
    val responseJson = parse(response.bodyString)

    case class ResponseJSONStructure(
      password: String,
      id: String,
      email: String,
      login_url: String
    )

    val createdFacebookTestUser = responseJson.extract[ResponseJSONStructure]

    val mergedFacebookTestUser = FacebookTestUser(
      facebookTestUser.fullName,
      facebookTestUser.installed,
      Some(createdFacebookTestUser.password),
      facebookTestUser.locale,
      facebookTestUser.permissions,
      facebookTestUser.method,
      Some(createdFacebookTestUser.id),
      Some(createdFacebookTestUser.email),
      Some(createdFacebookTestUser.login_url),
      true
    )

    mergedFacebookTestUser
  }

  def deleteUser(facebookTestUser: FacebookTestUser, accessToken: AccessToken): Boolean = {
    accessToken.retrieve()

    val payload = Map(
      "access_token"-> accessToken.value.getOrElse("")
    ).map { case (k, v) => s"$k=" + URLEncoder.encode(v.toString)}.mkString("&")

    val url = new URL(s"$graphApiLocation/v$graphApiVersion/${facebookTestUser.id.getOrElse("")}?$payload")
    val response = Await.result(DELETE(url).apply, 30.second)
    val responseJson = parse(response.bodyString)

    case class ResponseJSONStructure(success: Boolean)

    val result = responseJson.extract[ResponseJSONStructure]

    result.success
  }


}