package it.sf.service
import play.api.db.slick.Config.driver.simple._
import it.sf.models.{Interest, User, InterestTable}
import play.api.db.slick._
import play.api.Play.current

/**
 * Created by diego on 15/05/14.
 */
trait InterestService extends UserInterestService {
  val interests = TableQuery[InterestTable]

  def getUserInterests(user: User): List[Interest] = {
    play.api.db.slick.DB.withSession {
      implicit session => {
          val q = for {
            (i, ui) <- interests innerJoin userInterests on (_.id === _.idInterest)
            if (ui.idUser === user.id.get)
          } yield i
          q.list
      }
    }
  }
}
