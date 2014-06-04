package it.sf.repository

import it.sf.models.{Interest, InterestTable}
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
/**
 * Created by diego on 15/05/14.
 */
class InterestRepository extends UserInterestRepository {
  val interests = TableQuery[InterestTable]

  def getUserInterests(userId: Long): List[Interest] = {
    play.api.db.slick.DB.withSession {
      implicit session => {
          val q = for {
            (i, ui) <- interests innerJoin userInterests on (_.id === _.idInterest)
            if ui.idUser === userId
          } yield i
          q.list
      }
    }
  }

  def checkUserInterest(name: String, userId: Long) = {
    play.api.db.slick.DB.withSession {
      implicit session => {
        getUserInterests(userId).find(_.name.equals(name))
      }
    }
  }

  def insertInterestToUser(name: String, userId: Long) = play.api.db.slick.DB.withSession {
    implicit session => {
      val firstOption: Option[InterestTable#TableElementType] = interests.filter(_.name === name).firstOption

      firstOption.getOrElse {
        //TODO id category
        val interestId: Long = (interests returning interests.map(_.id))+= Interest(None, name, userId, Some(1))
        insertUserInterest(userId, interestId)
      }

    }
  }
}
