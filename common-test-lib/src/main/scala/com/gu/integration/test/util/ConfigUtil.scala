package com.gu.integration.test.util

import com.gu.integration.test.util.FileUtil._

object ConfigUtil {

  def getConfigFilename(): String = {
    val configFilename = System.getProperty("config.resource") match {
      case filename: String => filename
      case null => "local.conf"
    }
    s"${currentPath()}/identity-tests/${configFilename}"
  }

}
