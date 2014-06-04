package it.sf.repository

import scala.slick.lifted.TableQuery
import org.joda.time.DateTime
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{Configuration, ConfigurationTable}

/**
 * Created by diego on 15/05/14.
 */
class ConfigurationRepository {
  val configurations = TableQuery[ConfigurationTable]

  def insertConfiguration(key: String, value: String) {
    play.api.db.slick.DB.withSession {
      implicit session => {
        configurations += Configuration(None, key, value, DateTime.now)
      }
    }
  }

  def findConfigurationByKey(key: String): Option[Configuration] = {
    play.api.db.slick.DB.withSession {
      implicit session => {
        configurations.filter(_.key === key).firstOption
      }
    }
  }
}
