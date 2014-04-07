package service
import play.api.Play.current
import models.Users
import models.User
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import Database._
import play.api.db.DB

object UserService2 {
  val users = TableQuery[Users]
  val db = Database.forDataSource(DB.getDataSource())
  def insertUser(user: User) = {
    db.withDynSession({
      val num = users += user
      num
    })
  }

}