package services

import org.junit.runner.RunWith
import it.sf.service._
import org.specs2.ScalaCheck
import it.sf.models.{UserTable, User}
import scala.collection.mutable
import org.scalacheck.{Arbitrary, Gen}
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import securesocial.core.AuthenticationMethod
import it.sf.service.UserValidation
import scala.Some
import it.sf.manager.ComponentRegistry
import it.sf.repository.UserRepositoryInterface
import scala.slick.lifted.TableQuery

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class UserSpec extends Specification with ScalaCheck with MockUserRegistry {

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
        var counter = 0
        val insertUsersResult: Boolean = usernames.foldLeft(true) {
          (oldValue: Boolean, username: String) =>
            val userValidation: UserValidation = userService.insertUser(username, "test", "Diego", "Fabbro", AuthenticationMethod.UserPassword, "userpass")
            if ("".equals(username)) {
              "Wrong values".equals(userValidation.errorMessage) &&
                !userValidation.result &&
                userValidation.user.isEmpty
            } else {
              if (userValidation.user.isDefined) {
                counter = counter + 1
              }
              (userValidation.user.isDefined && userValidation.result) ||
                (!userValidation.result && userValidation.errorMessage.contains("already exists") && userValidation.user.isEmpty)
            }
        }
//                classify(counter < 1, "less 1 insert") {
//
//                  classify(counter < 50, "less 50 insert", "over 50 insert") {
//
//                    resultInsertingUsernames
//                  }
//                }
        import org.scalacheck.Prop._

//        val names = usernames.map(value => {
//          if ("".equals(value)) "blank"
//          else value
//        }).mkString(",")
//        classify(counter == 0, s"no insert and size ${usernames.length} (values:$names)") {
          collect(getLabel(counter)) {
            insertUsersResult
          }
//        }
      }
    }
  }

  def getLabel(value: Int) = {
    ((value + 9) / 10) * 10 match {
      case 0 => s"0 inserts"
      case v => s"[${v - 9}-$v inserts]"
    }
  }
}

class MockUserRepository extends UserRepositoryInterface {
  var mutableList = mutable.LinkedList[User]()
  override def dbInsertUser(user: User): Long = {
    mutableList = mutableList.+:(user)
    mutableList.size
  }

  override def dbFindUserByUserName(username: String): Option[User] = {
    val filter: mutable.LinkedList[User] = mutableList.filter(_.username.equals(username))
    if (filter.size > 0) {
//      if (username.length > 1) {
//        print(s"$username already exists - ")
//        println(s"list: ${mutableList.size}")
//      }
      Some(filter.head)
    } else {
      None
    }
  }

  //  val users: lifted.TableQuery[UserTable]
  override def dbFindUserByUsername(username: String, password: String): Option[User] = None

  override def dbGetUsersList(): List[User] = List.empty

  override def dbUpdatePassword(username: String, password: String): Unit = {}

  override def dbFindUsers(ids: Seq[Long]): Seq[User] = Seq.empty

  override def dbFindUserById(id: Long): Option[User] = None

  override val users: TableQuery[UserTable] = null
}

trait MockUserRegistry extends ComponentRegistry {
  override lazy val userRepository = new MockUserRepository
  override lazy val userService = new UserService(userRepository)
}