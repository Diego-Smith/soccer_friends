package it.sf.conf

import play.api.Play.current
import it.sf.models.Users
import it.sf.models.User
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB
import it.sf.service.UserService2._
import scala.util.control.TailCalls

trait StartupTableFiller {
  def fillUserTable() = {
    insertUser(User(None, "user1", "user1@gmail.com"))
    insertUser(User(None, "user2", "user2@gmail.com"))
    insertUser(User(None, "admin", "admin@gmail2.com"))
  }
}