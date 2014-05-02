package services


import org.junit.runner.RunWith
import org.specs2.execute.AsResult
import org.specs2.execute.Result
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import play.api.test.Helpers._
import it.sf.conf.StartupTableFiller
import it.sf.service.FriendshipService


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class FriendshipSpec extends Specification {

  val appWithMemoryDatabase = FakeApplication(additionalConfiguration = inMemoryDatabase())

//  abstract class WithDbData extends WithApplication() {
//    override def around[T: AsResult](t: => T): Result = super.around {
//      setupData()
//      t
//    }
//
//    def setupData() {
//      println("test")
//      StartupTableFiller.startupFill
//
//    }
//  }


  "Friendship Service" should {

    "insert correctly the friendship record" in new WithApplication() {

      val FriendshipService = new FriendshipService{}

      println(FriendshipService.friendshiplist)
    }

  }
}
