package services


import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import play.api.test.Helpers._
import it.sf.service.{UserValidation, UserService, FriendshipService}
import it.sf.models.{Friendship, User}


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class FriendshipSpec extends Specification {

  "Friendship Service" should {

    "insert correctly the friendship record" in new WithApplication() {

      val friendshipService = new FriendshipService {}

      val userService = new UserService {}

      val user1 = userService.insertUser("u1", "pass").user
      val user2 = userService.insertUser("u2", "pass2").user
      val user3Result : UserValidation = userService.insertUser("u2", "pass2")
      val user3 = user3Result.user
      user1 must not be empty
      user2 must not be empty
      user3 must empty

      private val friendship = friendshipService.insertFriendship(Friendship(None, user1.get.id.get, user2.get.id.get)).friendship
      private val friendship1 = friendshipService.insertFriendship(Friendship(None, user2.get.id.get, user1.get.id.get)).friendship

      friendship must not be none
      friendship1 must not be none

      friendship.get.idUserA mustEqual friendship1.get.idUserA
    }

  }
}
