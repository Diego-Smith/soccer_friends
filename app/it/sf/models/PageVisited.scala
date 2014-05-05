package it.sf.models

import java.sql.Date
import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.UserService
import java.util.Calendar

case class PageVisited(id: Option[Int] = None, pagename: String, ip: String, val dateVisited: Date = new Date(Calendar.getInstance().getTimeInMillis()),
                       fkUser: Option[Int] = None)

class PageVisitedTable(tag: Tag) extends Table[PageVisited](tag, "PAGE_VISITED") with UserService {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def pagename = column[String]("PAGE_NAME", O.NotNull)

  def ip = column[String]("IP", O.NotNull)

  def date = column[Date]("DATE", O.NotNull)

  def fkUser = column[Int]("ID_USER")

  def * = (id.?, pagename, ip, date, fkUser.?) <>(PageVisited.tupled, PageVisited.unapply)

  def user = foreignKey("FK_USER", fkUser, users)(_.id)
}

