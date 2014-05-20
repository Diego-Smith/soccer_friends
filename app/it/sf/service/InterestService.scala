package it.sf.service
import play.api.db.slick.Config.driver.simple._
import it.sf.models.{Interest, User, InterestTable}
import play.api.db.slick._
import play.api.Play.current
import scala.slick.driver.JdbcDriver

/**
 * Created by diego on 15/05/14.
 */
trait InterestService extends UserInterestService {
  val interests = TableQuery[InterestTable]

  def getUserInterests(userId: Long): List[Interest] = {
    play.api.db.slick.DB.withSession {
      implicit session => {
          val q = for {
            (i, ui) <- interests innerJoin userInterests on (_.id === _.idInterest)
            if (ui.idUser === userId)
          } yield i
          q.list
      }
    }
  }

  def checkUserInterest(name: String, userId: Long) = {
    play.api.db.slick.DB.withSession {
      implicit session => {
        getUserInterests(userId).filter(_.name.equals(name)).headOption
      }
    }
  }

  def insertInterestToUser(name: String, userId: Long) = {
    play.api.db.slick.DB.withSession {
      implicit session => {
        val firstOption: Option[InterestTable#TableElementType] = interests.filter(_.name === name).firstOption

        val interest = firstOption.getOrElse {
          //TODO id category
          val interestId: Long = (interests returning interests.map(_.id))+= Interest(None, name, userId, Some(1))
          insertUserInterest(userId, interestId)
        }

      }
    }
  }
}
