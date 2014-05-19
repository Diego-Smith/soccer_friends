package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.UserService
import com.github.tototoshi.slick.H2JodaSupport._
import org.joda.time.DateTime

case class PageVisited(id: Option[Int] = None, pagename: String, ip: String, dateVisited: DateTime = DateTime.now(),
                       fkUser: Option[Int] = None)

class PageVisitedTable(tag: Tag) extends Table[PageVisited](tag, "PAGE_VISITED") with UserService {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def pagename = column[String]("PAGE_NAME", O.NotNull)
  def ip = column[String]("IP", O.NotNull)
  def date = column[DateTime]("DATE", O.NotNull)
  def fkUser = column[Int]("ID_USER", O.Nullable)
//  val expirationTime = column[DateTime]("EXPIRATION_TIME", O.NotNull)

  def * = (id.?, pagename, ip, date, fkUser.?) <>(PageVisited.tupled, PageVisited.unapply)

  def user = foreignKey("FK_USER", fkUser, users)(_.id)
}

