package it.sf.service

import scala.slick.lifted.TableQuery
import it.sf.models.UserInterest.UserInterestTable
import play.api.db.slick.Config.driver.simple._

/**
 * Created by diego on 15/05/14.
 */
trait UserInterestService {
  val userInterests = TableQuery[UserInterestTable]
}
