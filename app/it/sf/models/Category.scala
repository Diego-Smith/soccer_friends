package it.sf.models

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.Config.driver.simple.Tag

case class Category(id: Option[Long] = None, name: String)

class CategoryTable(tag: Tag) extends Table[Category](tag, "CATEGORY") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("USERNAME", O.NotNull)

  def * = (id.?, name) <>(Category.tupled, Category.unapply)
}
