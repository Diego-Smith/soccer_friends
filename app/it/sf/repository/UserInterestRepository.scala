package it.sf.repository

import scala.slick.lifted.TableQuery
import it.sf.models.UserInterest.{UserInterest, UserInterestTable}
import org.joda.time.DateTime
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
/**
 * Created by diego on 15/05/14.
 */
class UserInterestRepository {
  val userInterests = TableQuery[UserInterestTable]

  def insertUserInterest(idUser: Long, idInterest: Long) {
    play.api.db.slick.DB.withSession {
      implicit session => {
        userInterests += UserInterest(None, idUser, idInterest, DateTime.now)
      }
    }
  }

}
