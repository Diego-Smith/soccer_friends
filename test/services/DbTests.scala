package services

import org.junit.runner.RunWith
import it.sf.service.{InterestRepository, OAuth2Repository}
import org.specs2.ScalaCheck
import play.api.test.WithApplication
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import scala.slick.lifted.{CompiledFunction, TableQuery}
import it.sf.models.{User, OAuth2InfoTable}
import play.api.db.slick.Session
import scala.slick.lifted
import scala.slick.driver.H2Driver.simple._
import it.sf.manager.ComponentRegistry
import scala.None

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class DbTests extends Specification with ScalaCheck {

  "Tests" should {


    "test queries" in new WithApplication() {

      private val componentRegistry: ComponentRegistry = new ComponentRegistry {}
      private val userService = componentRegistry.userService
      private val interestService: InterestRepository with Object = new InterestRepository {}
      play.api.db.slick.DB.withSession {
        implicit session: Session => {
          val user4: Option[User] = userService.findUserById(4)
          println("interets:" + interestService.interests.list)

          user4 must beSome
          user4.get.id must beSome
          val interests = interestService.getUserInterests(user4.get.id.get)

          println("--------" + interests)
        }
      }

    }


  }
}
