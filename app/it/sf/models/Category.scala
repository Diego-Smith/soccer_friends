package it.sf.models

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.Config.driver.simple.Tag

case class Category(id: Option[Int] = None, name: String)

class Categories(tag: Tag) extends Table[Category](tag, "CATEGORY") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("USERNAME", O.NotNull)

  def * = (id.?, name) <>(Category.tupled, Category.unapply)
}
