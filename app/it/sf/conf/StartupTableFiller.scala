package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import play.api.db.DB
import it.sf.service.{UserService, CategoryService, FriendshipService}
import it.sf.models.Category
import securesocial.core.AuthenticationMethod
import play.api.libs.Crypto

object StartupTableFiller extends ApplicationLoggerImpl with UserService with CategoryService with FriendshipService {
  def obtainDB: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    obtainDB
    fillUserTable
    fillCategories
    fillFriendship
  }

  def fillUserTable() = {
    insertUser("user1", Crypto.sign("user1"), "User", "1", AuthenticationMethod.UserPassword, "userpass")
    insertUser("diego", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
  }

  def fillCategories() {


    insertCategory(Category(None, "book"))
    insertCategory(Category(None, "school"))
    insertCategory(Category(None, "sport"))
  }

  def fillFriendship() {
//    val friendshipService = new FriendshipService {}
//    insertFriendship(1,2)
//    insertFriendship(Friendship(None, 2, 1))
  }
}