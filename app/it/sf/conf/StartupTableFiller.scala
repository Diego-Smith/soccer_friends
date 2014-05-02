package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB
import it.sf.service.FriendshipService
import it.sf.models.User
import it.sf.models.Category

object StartupTableFiller extends ApplicationLoggerImpl {
  def db: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    fillUserTable
    fillCategories
    fillFriendship
  }

  def fillUserTable() = {
    val users = TableQuery[UserTable]
    db.withDynSession {
      users += User(None, "user1", "user1@gmail.com")
      users += User(None, "user1", "user1@gmail.com")
      users += User(None, "diego", "diego")
    }
  }

  def fillCategories {
    val categories = TableQuery[CategoryTable]
    db.withDynSession {
      categories += Category(None, "book")
      categories += Category(None, "school")
      categories += Category(None, "sport")
    }
  }

  def fillFriendship {
    val friendshipService = new FriendshipService {}
    friendshipService.insertFriendship(Friendship(None, 1, 2))
    friendshipService.insertFriendship(Friendship(None, 2, 1))
  }
}