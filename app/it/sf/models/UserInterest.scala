package it.sf.models.UserInterest

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.{InterestService, UserService, CategoryService}
import it.sf.models.{InterestTable, UserTable}
import org.joda.time.DateTime
import com.github.tototoshi.slick.H2JodaSupport._

case class UserInterest(id: Option[Long] = None, idUser: Long, idInterest: Long, creationTime: DateTime)

class UserInterestTable(tag: Tag) extends Table[UserInterest](tag, "USER_INTEREST") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def idUser = column[Long]("ID_USER", O.NotNull)
  def idInterest = column[Long]("ID_INTEREST", O.NotNull)
  def creationTime = column[DateTime]("CREATION_TIME", O.NotNull)

  def * = (id.?, idUser, idInterest, creationTime) <> (UserInterest.tupled, UserInterest.unapply)

  val interests = TableQuery[InterestTable]
  val users = TableQuery[UserTable]

  def interestRelation = foreignKey("UI_INTEREST_FK", idInterest, interests)(_.id)
  def userRelation = foreignKey("UI_USER_FK", idUser, users)(_.id)

  def uniqueInterest = index("UIT_UNIQUE", (idUser,idInterest), unique = true)
}


