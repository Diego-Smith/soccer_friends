import org.junit.runner.RunWith
import org.specs2.execute.AsResult
import org.specs2.execute.Result
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {
  val appWithMemoryDatabase = FakeApplication(additionalConfiguration = inMemoryDatabase())
  "Application" should {

    "send 404 on a bad request" in new WithApplication() {
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApplication() {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain("Your new application is ready.")
    }
  }

}
