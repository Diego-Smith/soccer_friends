package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.UserTable
import it.sf.models.User
import play.api.db.slick.DB
import play.api.db.slick.Session
import it.sf.logger.ApplicationLoggerImpl
import scala.slick.lifted
import securesocial.core._
import securesocial.core.providers.Token
import securesocial.core.IdentityId
import securesocial.core.providers.Token
import scala.Some
import it.sf.models.User

trait UserRepository {
  lazy val users: lifted.TableQuery[UserTable] = TableQuery[UserTable]

  def dbInsertUser(user: User): Int = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        (users returning users.map(_.id)) += user
      }
    }
  }

  def dbFindUserByUserName(username: String): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.username === username).firstOption
  }

}

trait UserService extends ApplicationLoggerImpl with UserRepository {

  def findUserByUsername(username: String, password: String): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(user => user.username === username && user.password === password).firstOption
  }

  def findUserById(id: Int): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.id === id).firstOption
  }

  class UserIdList[Int](val source: Seq[scala.Int]) {
    def getUsers: Seq[User] = {
      findUsers(source)
    }
  }

  def findUsers(ids: Seq[Int]): Seq[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.id inSetBind ids).list
  }

  def findUserByUsername(username: String): Option[User] = dbFindUserByUserName(username)

  def insertUser(user: User): UserValidation = validateUser(user) match {
    case (true, reason) => {
      val result = dbInsertUser(user)
      UserValidation(result = true, Some(user.copy(id = Some(result))), reason)
    }
    case (false, reason) => UserValidation(result = false, None, reason)
  }

  def insertUser(username: String, password: String): UserValidation = {
    val user = User(None, username, password)
    insertUser(user)
  }

  def validateUser(user: User) = {
    val checkedUser = findUserByUsername(user.username)
    if (!checkedUser.isEmpty) {
      logConsole(s"User ${
        user.username
      } already exists")
      (false, s"User ${
        user.username
      } already exists")
    } else {
      user match {
        case User(None, username, password) =>
          if (username.isEmpty || password.isEmpty) {
            (false, "Wrong values")
          } else {
            (true, "")
          }
        case _ => (false, "Wrong values")
      }
    }
  }

  def getUsersList = DB.withSession {
    implicit session: Session =>
      users.list

  }

}

import play.api.Application
class UserServicePluginImpl(application: Application) extends UserServicePlugin(application: Application) with UserService {
  override def deleteExpiredTokens(): Unit = {}

  override def deleteToken(uuid: String): Unit = {}

  override def findToken(token: String): Option[Token] = {
    println("findToken")
    None
  }

  override def save(token: Token): Unit = {}

  override def save(user: Identity): Identity = {
    SocialUser(user)
  }

  override def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = None

  override def find(id: IdentityId): Option[Identity] = {
    val pass: PasswordInfo = PasswordInfo.apply("md5", "diego", None)
//    val socialUser: SocialUser = SocialUser.apply(id, "Diego", "Fabbro", "diego Fabbro", None, None, AuthenticationMethod.UserPassword, None, None, Some(pass))
    val socialUser: SocialUser = SocialUser.apply(id, "Diego", "Fabbro", "Diego Fabbro", Some("diego.naali@gmail.com"), None ,AuthenticationMethod.UserPassword, None, None, Some(pass))

    val optionUser: Option[User] = findUserByUsername(id.userId)
    optionUser match {
      case Some(user) => Some(socialUser)
      case None => None
    }

  }
}


case class UserValidation(result: Boolean, user: Option[User], errorMessage: String)
