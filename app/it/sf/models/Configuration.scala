package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.H2JodaSupport._

case class Configuration(id: Option[Long] = None, key: String, value: String, creationTime: DateTime)

class ConfigurationTable(tag: Tag) extends Table[Configuration](tag, "CONFIGURATION") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def key = column[String]("KEY", O.NotNull)
  def value = column[String]("VALUE", O.NotNull)
  def creationTime = column[DateTime]("CREATION_TIME", O.NotNull)

  def * = (id.?, key, value, creationTime) <> (Configuration.tupled, Configuration.unapply)

  def uniqueConfiguration = index("CONF_UNIQUE", key, unique = true)
}



