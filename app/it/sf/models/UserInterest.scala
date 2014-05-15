package it.sf.models.UserInterest

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.{InterestService, UserService, CategoryService}
import it.sf.models.{InterestTable, UserTable}

case class UserInterest(id: Option[Int] = None, idUser: Int, idInterest: Int)

class UserInterestTable(tag: Tag) extends Table[UserInterest](tag, "USER_INTEREST") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def idUser = column[Int]("ID_USER", O.NotNull)
  def idInterest = column[Int]("ID_INTEREST", O.NotNull)

  def * = (id.?, idUser, idInterest) <> (UserInterest.tupled, UserInterest.unapply)

  val interests = TableQuery[InterestTable]
  val users = TableQuery[UserTable]

  def interestRelation = foreignKey("UI_INTEREST_FK", idInterest, interests)(_.id)
  def userRelation = foreignKey("UI_USER_FK", idUser, users)(_.id)
}


