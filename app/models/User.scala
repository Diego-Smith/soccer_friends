package models
import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.DB
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
case class User(name: String, id: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "user") {
  // Auto Increment the id primary key column
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  // The name can't be null
  def username = column[String]("username", O.NotNull)
  // the * projection (e.g. select * ...) auto-transform the tupled column values to / from a User
  def * = (username, id.?) <> (User.tupled, User.unapply)
}

