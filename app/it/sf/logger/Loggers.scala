package it.sf.logger

import play.Logger

trait ApplicationLogger {
  def info(text: String): Unit
}

trait LoggerManager {

  val logger = new SoccerFriendsLogger
  val LOG_ROW_PREFIX = "[soccerFriends] :: "
  val LOG_PREFIX = ""
  val LOG_POSTFIX = LOG_PREFIX

  class SoccerFriendsLogger extends ApplicationLogger {
    def info(text: String) = {
      Logger.info(text.split("\n").map(s => s.mkString(LOG_ROW_PREFIX, "", "\n")).mkString(LOG_PREFIX, "", LOG_POSTFIX)
      )
    }
  }
}


trait Test extends LoggerManager {
  logger.info("asd")
  val test = new SoccerFriendsLogger
  test.info("prova")
}

object Runnable extends Test{
  def main(args: Array[String]) {
    println("test")
    test.info("ahuz")
  }
}