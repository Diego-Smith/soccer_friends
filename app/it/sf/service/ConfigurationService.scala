package it.sf.service

import scala.slick.lifted.TableQuery
import it.sf.models.UserInterest.{ConfigurationTable, Configuration, UserInterestTable, UserInterest}
import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import play.api.db.slick._
import play.api.Play.current

/**
 * Created by diego on 15/05/14.
 */
trait ConfigurationService {
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
