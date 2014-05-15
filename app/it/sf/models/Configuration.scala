package it.sf.models.UserInterest

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.{InterestService, UserService, CategoryService}
import it.sf.models.{InterestTable, UserTable}
import org.joda.time.DateTime
import com.github.tototoshi.slick.H2JodaSupport._

case class Configuration(id: Option[Int] = None, key: String, value: String, creationTime: DateTime)

class ConfigurationTable(tag: Tag) extends Table[Configuration](tag, "CONFIGURATION") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def key = column[String]("ID_USER", O.NotNull)
  def value = column[String]("ID_INTEREST", O.NotNull)
  def creationTime = column[DateTime]("CREATION_TIME", O.NotNull)

  def * = (id.?, key, value, creationTime) <> (Configuration.tupled, Configuration.unapply)

  def uniqueConfiguration = index("CONF_UNIQUE", (key), unique = true)
}



