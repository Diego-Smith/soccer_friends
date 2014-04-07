package conf

import play.api.Play.current
import models.Users
import models.User
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB
import service.UserService2._

trait StartupTableFiller {
  def fillUserTable() = {
    insertUser(User(None,"gmail","gmail.com"))
    insertUser(User(None,"diego","gmail2.com"))
    insertUser(User(None,"gmail","gmail2.com"))
  }
}