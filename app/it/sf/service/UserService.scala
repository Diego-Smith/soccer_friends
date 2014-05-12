package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{AuthenticationMethodEnum, UserTable, User}
import play.api.db.slick.DB
import play.api.db.slick.Session
import it.sf.logger.ApplicationLoggerImpl
import scala.slick.lifted
import securesocial.core._
import securesocial.core.IdentityId
import securesocial.core.providers.Token
import scala.Some
import it.sf.models.ProviderIdEnum.ProviderIdEnum

trait UserRepository {
  val users: lifted.TableQuery[UserTable] = TableQuery[UserTable]
  private val insertReturningId = (users returning users.map(_.id))
  def dbInsertUser(user: User): Int = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        insertReturningId += user
      }
    }
  }

  def dbInsertUser2(user: User): Int = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        val insertReturningId2 = users returning users.map(_.id)
        insertReturningId2 += user
      }
    }
  }


  private val invoker = insertReturningId.insertInvoker


  def dbInsertUser3(user: User): Int = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        invoker.insert(user)
      }
    }
  }

  private val invoker2 = users.insertInvoker
  def dbInsertUser4(user: User) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
          invoker2.insert(user)
          true
      }
    }
  }


  def dbInsertUser5(user: User) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        users += user
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

  def insertUser(username: String, password: String, name: String, surname: String, authMethod: AuthenticationMethod, provider: String): UserValidation = {
    val user = User(None, username, password, Some(name), Some(surname), AuthenticationMethodEnum.getValueByAuthenticationMethod(authMethod), provider)
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
        case User(None, username, password, _, _ , _, _) =>
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
case class UserValidation(result: Boolean, user: Option[User], errorMessage: String)
