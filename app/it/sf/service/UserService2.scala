package it.sf.service
import play.api.Play.current
import it.sf.models.Users
import it.sf.models.User
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB

object UserService2 {  //TODO: make a unique trait with UserService
  val users = TableQuery[Users]
  val db = Database.forDataSource(DB.getDataSource())
  def insertUser(user: User) = {
    db.withDynSession({
      val num = users += user
      num
    })
  }

}