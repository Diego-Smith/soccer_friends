package services


import org.junit.runner.RunWith
import it.sf.service.{FriendshipService, UserService}
import org.specs2.ScalaCheck
import play.api.test.WithApplication
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class FriendshipSpec extends Specification with ScalaCheck {

  "Friendship Service" should {


    "get Correct Friends" in new WithApplication() {
      val friendshipService = new FriendshipService {}
      val userService = new UserService {}
      val user1 = userService.insertUser("u1", "pass").user
      val user2 = userService.insertUser("u2", "pass2").user
      val user3 = userService.insertUser("u3", "pass2").user
      val user4 = userService.insertUser("u4", "pass2").user
      val user5 = userService.insertUser("u5", "pass2").user
      val user6 = userService.insertUser("u6", "pass2").user

      friendshipService.insertFriendship(user1.get.id.get, user2.get.id.get)
      friendshipService.insertFriendship(user1.get.id.get, user3.get.id.get)
      friendshipService.insertFriendship(user1.get.id.get, user4.get.id.get)
      friendshipService.insertFriendship(user1.get.id.get, user5.get.id.get)
      friendshipService.insertFriendship(user1.get.id.get, user6.get.id.get)

      val friends = friendshipService.getFriends(user1.get.id.get)
      friends must size(5)
      //          friends foreach println

    }


  }
}
