package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import play.api.db.DB
import it.sf.service.{UserService, CategoryService, FriendshipService}
import it.sf.models.Category

object StartupTableFiller extends ApplicationLoggerImpl with UserService with CategoryService with FriendshipService {
  def db: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    db
    fillUserTable
    fillCategories
    fillFriendship
  }

  def fillUserTable() = {
//    val userService = new UserService {}
    insertUser("user1", "user1@gmail.com")
    insertUser("user2", "user1@gmail.com")
    insertUser("diego", "diego")
  }

  def fillCategories() {

//    val categoryService = new CategoryService {}

    insertCategory(Category(None, "book"))
    insertCategory(Category(None, "school"))
    insertCategory(Category(None, "sport"))
  }

  def fillFriendship() {
//    val friendshipService = new FriendshipService {}
    insertFriendship(Friendship(None, 1, 2))
    insertFriendship(Friendship(None, 2, 1))
  }
}