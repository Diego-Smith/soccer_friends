package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import play.api.db.DB
import it.sf.service.{OAuth2Service, UserService, CategoryService, FriendshipService}
import it.sf.models.Category
import securesocial.core.AuthenticationMethod
import play.api.libs.Crypto

object StartupTableFiller extends ApplicationLoggerImpl with UserService with CategoryService with FriendshipService with OAuth2Service {
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
    insertUser("diego.naali@gmail.com", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    insertUser("10203749685571466facebook", Crypto.sign("password"), "Diego", "Fabbro", AuthenticationMethod.OAuth2, "facebook")
    insertOrUpdateOauth2(OAuth2Info(4, "cAAKGvjUQs9QBAB9kXPWzsn3mYZCRlZAg4GDUMIswvAPHJyN90O3WWCDfXWZCBG1m5ZAnFAmLXWJYbKMHJNxMZA7Dx9vNuuD17HR0oJX32RL3uigGZBqSrocZC68fuXp8uzixbZAjBFO2CLNYlUo5TEv0G1dm0BAv3SvfyvHeYHnOMTNY4fi74CIcdeX1yNyZBtsEZD",
      None,Some(5108366),None))
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