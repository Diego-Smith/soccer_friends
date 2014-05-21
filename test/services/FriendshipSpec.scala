package services


import org.junit.runner.RunWith
import it.sf.service.FriendshipService
import org.specs2.ScalaCheck
import play.api.test.WithApplication
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import securesocial.core.AuthenticationMethod
import it.sf.manager.ComponentRegistry

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
      private val componentRegistry: ComponentRegistry = new ComponentRegistry {}
      private val userService = componentRegistry.userService
      val user1 = userService.insertUser("user1", "user1", "User", "1", AuthenticationMethod.UserPassword, "userpass").user
      val user2 = userService.insertUser("user2", "user1", "User", "1", AuthenticationMethod.UserPassword, "userpass").user
      val user3 = userService.insertUser("user3", "user1", "User", "1", AuthenticationMethod.UserPassword, "userpass").user
      val user4 = userService.insertUser("user4", "user1", "User", "1", AuthenticationMethod.UserPassword, "userpass").user
      val user5 = userService.insertUser("user5", "user1", "User", "1", AuthenticationMethod.UserPassword, "userpass").user
      val user6 = userService.insertUser("user6", "user1", "User", "1", AuthenticationMethod.UserPassword, "userpass").user

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
