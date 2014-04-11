package it.sf.conf

import it.sf.logger.ApplicationLogger
import play.api.Play.current
import it.sf.models.{Categories, Category, Users, User}
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB

object StartupTableFiller extends ApplicationLogger {
  val db: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def fillUserTable() = {
    val users = TableQuery[Users]
    db.withDynSession{
      users += User(None, "user1", "user1@gmail.com")
//      applicationLogger("inserted user: " + User(None, "user1", "user1@gmail.com"))
      users += User(None, "user1", "user1@gmail.com")
      users += User(None, "diego", "diego")
    }
  }

  def fillCategories {
    val categories = TableQuery[Categories]
    db.withDynSession{
      categories += Category(None, "book")
      categories += Category(None, "school")
      categories += Category(None, "sport")
    }
  }
}