package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.impl.CategoryService
import it.sf.manager.ComponentRegistry
import scala.slick.lifted
import scala.slick.lifted.TableQuery

case class Interest(id: Option[Long] = None, name: String, createdByUserId: Long, idCategory: Option[Long])

class InterestTable(tag: Tag) extends Table[Interest](tag, "INTEREST") with CategoryService {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME", O.NotNull)
  def createdByUserId = column[Long]("CREATED_BY_USER_ID", O.NotNull)
  def idCategory = column[Long]("ID_CATEGORY")

  def * = (id.?, name, createdByUserId, idCategory.?) <>(Interest.tupled, Interest.unapply)

  def categoryRelation = foreignKey("CAT_FK", idCategory, categories)(_.id)

  val users: lifted.TableQuery[UserTable] = TableQuery[UserTable]
  def userRelation = foreignKey("USER_FK", createdByUserId, users)(_.id)
}


