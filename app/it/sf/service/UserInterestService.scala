package it.sf.service

import scala.slick.lifted.TableQuery
import it.sf.models.UserInterest.UserInterestTable
import play.api.db.slick.Config.driver.simple._
import org.joda.time.DateTime
import play.api.db.slick._
import it.sf.models.UserInterest.UserInterest
import play.api.Play.current
/**
 * Created by diego on 15/05/14.
 */
trait UserInterestService {
  val userInterests = TableQuery[UserInterestTable]

  def insertUserInterest(idUser: Int, idInterest: Int) {
    play.api.db.slick.DB.withSession {
      implicit session => {
        userInterests += UserInterest(None, idUser, idInterest, DateTime.now)
      }
    }

  }
}
