package it.sf.logger

import play.Logger
import scala.runtime.AbstractPartialFunction

trait ApplicationLogger {
  def info(text: String): Unit
}

trait LoggerManager {

  val logger = new LoggerSoccerFriends
  val LOG_ROW_PREFIX = "[soccerFriends] :: "
  val LOG_PREFIX = ""
  val LOG_POSTFIX = LOG_PREFIX

  class LoggerSoccerFriends extends ApplicationLogger {
    def info(text: String) = {
      Logger.info(text.split("\n").map(s => s.mkString(LOG_ROW_PREFIX, "", "\n")).mkString(LOG_PREFIX, "", LOG_POSTFIX)
      )
    }
  }
}