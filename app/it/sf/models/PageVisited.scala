package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.H2JodaSupport._
import org.joda.time.DateTime
import scala.slick.lifted
import scala.slick.lifted.TableQuery

case class PageVisited(id: Option[Long] = None, pagename: String, ip: String, dateVisited: DateTime = DateTime.now(),
                       userId: Option[Long] = None)

class PageVisitedTable(tag: Tag) extends Table[PageVisited](tag, "PAGE_VISITED") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def pagename = column[String]("PAGE_NAME", O.NotNull)
  def ip = column[String]("IP", O.NotNull)
  def date = column[DateTime]("DATE", O.NotNull)
  def userId = column[Long]("ID_USER", O.Nullable)
//  val expirationTime = column[DateTime]("EXPIRATION_TIME", O.NotNull)

  def * = (id.?, pagename, ip, date, userId.?) <>(PageVisited.tupled, PageVisited.unapply)

  val users: lifted.TableQuery[UserTable] = TableQuery[UserTable]
  def user = foreignKey("FK_USER", userId, users)(_.id)
}

