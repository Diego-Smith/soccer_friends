package services

import org.junit.runner.RunWith
import it.sf.service.{InterestService, OAuth2Service}
import org.specs2.ScalaCheck
import play.api.test.WithApplication
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import scala.slick.lifted.{CompiledFunction, TableQuery}
import it.sf.models.{User, OAuth2InfoTable}
import play.api.db.slick.Session
import scala.slick.lifted
import scala.slick.driver.H2Driver.simple._
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class DbTests extends Specification with ScalaCheck {

  "Tests" should {


    "test queries" in new WithApplication() {

      private val interestService: InterestService with Object = new InterestService {}

      play.api.db.slick.DB.withSession {
        implicit session: Session => {
          val user4: Option[User] = interestService.findUserById(4)
          println("interets:" + interestService.interests.list)
          val interests = interestService.getUserInterests(user4.get)
          println("--------" + interests)
        }
      }

    }


  }
}
