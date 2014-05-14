package it.sf.logger

import play.Logger

trait ApplicationLogger {
  def logConsole(text: String): Unit
}

trait ApplicationLoggerImpl extends ApplicationLogger {
  val LOG_ROW_PREFIX = "[soccerFriends] :: "
  val LOG_PREFIX = ""
  val LOG_POSTFIX = LOG_PREFIX

  override def logConsole(text: String) = {
    Logger.info(
      text.split("\n").map(s => s.mkString(LOG_ROW_PREFIX, "", "\n")).mkString(LOG_PREFIX, "", LOG_POSTFIX))
  }

}