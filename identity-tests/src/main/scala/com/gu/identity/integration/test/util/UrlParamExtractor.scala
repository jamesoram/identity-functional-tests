package com.gu.identity.integration.test.util

import java.net.URI

import scala.util.Try

object UrlParamExtractor {

  def extractQueryParams(url: String): Map[String, String] =
  {
    def keyValuePair(queryParam: String): (String, String) = {
      (queryParam.split("=")(0), Try(queryParam.split("=").tail.mkString("=")).toOption.getOrElse(""))
    }

    val uri = new URI(url)
    Option(uri.getQuery) match {
      case Some(query) => {
        query.split("&").map(keyValuePair).map(p => p._1 -> p._2).toMap
      }
      case _ => Map()
    }
  }

  def returnUrl(url: String): Option[URI] = {
    extractQueryParams(url).get("returnUrl") match {
      case Some(returnUrl) => Try(new URI(returnUrl)).toOption
      case _ => None
    }
  }

}
