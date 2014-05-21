package services

import org.junit.runner.RunWith
import it.sf.service._
import org.specs2.ScalaCheck
import it.sf.models.{ProviderIdEnum, User}
import scala.collection.mutable
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Prop._
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import securesocial.core.AuthenticationMethod
import play.api.libs.Crypto
import it.sf.service.UserValidation
import scala.Some
import it.sf.models.User
import org.specs2.mock.Mockito
import it.sf.repository.UserRepositoryComponent

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class UserSpec extends Specification with ScalaCheck {

  val caseValues: Seq[Char] = "abcdefghijklmnopqrstuvwxyz123456780!£&".toSeq

  implicit def a: Arbitrary[String] = Arbitrary {
    usernameGenerator
  }

  val usernameGenerator: Gen[String] = {
    val gen = for {
      n <- Gen.frequency((1, 0), (2, 1), (3, 2), (4, 3), (5, 4), (6, 5), (7, 6), (6, 7))
      listOfN <- Gen.listOfN(n, Gen.oneOf(caseValues))
    } yield listOfN
    gen.map(_.mkString(""))
  }

  "UserService" should {

    "test scala check" in prop {
      (usernames: List[String]) => {
        val userService = MockUserRepository.userService
        var counter = 0
        val resultInsertingUsernames: Boolean = usernames.foldLeft(true) {
          (oldValue: Boolean, username: String) =>
            val userValidation: UserValidation = userService.insertUser(username, Crypto.sign("user1"), "Diego", "Fabbro", AuthenticationMethod.UserPassword, "userpass")
            if ("".equals(username)) {
              "Wrong values".equals(userValidation.errorMessage) && !userValidation.result && userValidation.user.isEmpty
            } else {
              if (userValidation.user.isDefined) {
                counter = counter + 1
              }
              (userValidation.user.isDefined && userValidation.result) ||
                (!userValidation.result && userValidation.errorMessage.contains("already exists") && userValidation.user.isEmpty)
            }
        }

        //        classify(counter < 1, "less 1 insert") {
        //
        //          classify(counter < 50, "less 50 insert", "over 50 insert") {
        //
        //            resultInsertingUsernames
        //          }
        //        }

        val names = usernames.map(value => {
          if ("".equals(value)) "blank"
          else value
        }).mkString(",")
        classify(counter == 0, s"no insert and size ${usernames.length} (values:$names)") {
          collect(((counter + 9) / 10) * 10) {

            resultInsertingUsernames
          }
        }
      }
    }
  }
}

class MockUserRepository extends it.sf.repository.UserRepository {
  var mutableList = mutable.LinkedList[User]()

  override def dbInsertUser(user: User): Long = {
    mutableList = mutableList.+:(user)
    //    println(mutableList.size)
    mutableList.size
  }

  override def dbFindUserByUserName(username: String): Option[User] = {
    //    println(username)
    val filter: mutable.LinkedList[User] = mutableList.filter(_.username.equals(username))
    if (filter.size > 0) {
      Some(filter.head)
    } else {
      None
    }
  }
}

object MockUserRepository extends UserServiceComponent with UserRepositoryComponent with Mockito {
  val userService = new UserService
  val userRepository = new MockUserRepository
}