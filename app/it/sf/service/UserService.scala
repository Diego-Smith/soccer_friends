package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{AuthenticationMethodEnum, UserTable, User}
import play.api.db.slick.DB
import play.api.db.slick.Session
import it.sf.logger.ApplicationLoggerImpl
import scala.slick.lifted
import securesocial.core._
import it.sf.repository.{UserRepositoryInterface, UserRepository}

//trait UserServiceComponent {
//  this: UserRepositoryComponent =>
//  val userService: UserService

  class UserService(userRepository: UserRepositoryInterface) extends ApplicationLoggerImpl {

    def findUserByUsername(username: String, password: String): Option[User] = userRepository.dbFindUserByUsername(username, password)
    def findUserById(id: Long): Option[User] = userRepository.dbFindUserById(id)

    class UserIdList[Long](val source: Seq[scala.Long]) {
      def getUsers: Seq[User] = {
        findUsers(source)
      }
    }

    def findUsers(ids: Seq[Long]): Seq[User] = userRepository.dbFindUsers(ids)

    def findUserByUsername(username: String): Option[User] = userRepository.dbFindUserByUserName(username)

    def insertUser(user: User): UserValidation = validateUser(user) match {
      case (true, reason) =>
        val result = userRepository.dbInsertUser(user)
        UserValidation(result = true, Some(user.copy(id = Some(result))), reason)
      case (false, reason) => UserValidation(result = false, None, reason)
    }

    def insertUser(username: String, password: String, name: String, surname: String, authMethod: AuthenticationMethod, provider: String): UserValidation = {
      val user = User(None, username, password, Some(name), Some(surname), AuthenticationMethodEnum.getValueByAuthenticationMethod(authMethod), provider)
      insertUser(user)
    }

    def updatePassword(username: String, password: String) = userRepository.dbUpdatePassword(username, password)

    def validateUser(user: User) = {
      val checkedUser = findUserByUsername(user.username)
      if (!checkedUser.isEmpty) {
        logConsole(s"User ${user.username} already exists")
        (false, s"User ${user.username} already exists")
      } else {
        user match {
          case User(None, username, password, _, _, _, _) =>
            if (username.isEmpty || password.isEmpty) {
              (false, "Wrong values")
            } else {
              (true, "")
            }
          case _ => (false, "Wrong values")
        }
      }
    }

    def getUsersList() : List[User] = userRepository.dbGetUsersList()

  }

//}

case class UserValidation(result: Boolean, user: Option[User], errorMessage: String)
