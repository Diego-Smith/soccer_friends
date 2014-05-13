package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.CategoryService

case class Interest(id: Option[Int] = None, name: String, counter: Int, idCategory: Option[Int])

class InterestTable(tag: Tag) extends Table[Interest](tag, "INTEREST") with CategoryService {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("USERNAME", O.NotNull)
  def counter = column[Int]("COUNTER", O.Default(0), O.NotNull)
  def idCategory = column[Int]("ID_CATEGORY")

  def * = (id.?, name, counter, idCategory.?) <>(Interest.tupled, Interest.unapply)

  def category = foreignKey("CAT_FK", idCategory, categories)(_.id)
}


