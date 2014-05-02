package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB
import it.sf.service.{UserService, CategoryService, FriendshipService}
import it.sf.models.User
import it.sf.models.Category

object StartupTableFiller extends ApplicationLoggerImpl {
  def db: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    db
    fillUserTable
    fillCategories
    fillFriendship
  }

  def fillUserTable() = {
    val userService = new UserService {}
    userService.insertUser("user1", "user1@gmail.com")
    userService.insertUser("user2", "user1@gmail.com")
    userService.insertUser("diego", "diego")
  }

  def fillCategories {

    val categoryService = new CategoryService {}

    categoryService.insert(Category(None, "book"))
    categoryService.insert(Category(None, "school"))
    categoryService.insert(Category(None, "sport"))
  }

  def fillFriendship {
    val friendshipService = new FriendshipService {}
    friendshipService.insertFriendship(Friendship(None, 1, 2))
    friendshipService.insertFriendship(Friendship(None, 2, 1))
  }
}