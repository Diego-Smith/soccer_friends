package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.Config.driver.simple.Tag

case class User(id: Option[Int] = None, username: String, password: String)

class Users(tag: Tag) extends Table[User](tag, "USER") {
  // Auto Increment the id primary key column
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  // The name can't be null
  def username = column[String]("USERNAME", O.NotNull)

  def password = column[String]("PASSWORD", O.NotNull)

  // the * projection (e.g. select * ...) auto-transform the tupled column values to / from a User
  def * = (id.?, username, password) <>(User.tupled, User.unapply)
}

