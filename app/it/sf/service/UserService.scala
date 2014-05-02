package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.UserTable
import it.sf.models.User
import play.api.db.slick.DB
import play.api.db.slick.Session
import it.sf.logger.ApplicationLoggerImpl

trait UserService extends ApplicationLoggerImpl {
  val users = TableQuery[UserTable]

  def findUserByUsername(username: String, password: String): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(user => user.username === username && user.password === password).firstOption
  }

  def findUserById(id: Int) = DB.withSession {
    implicit  session: Session =>
      users.filter(_.id === id).firstOption
  }

  def findUserByUsername(username: String): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.username === username).firstOption
  }

  def insertUser(user: User): UserValidation = validateUser(user) match {
    case (true, reason) => {
      play.api.db.slick.DB.withSession {
        implicit session: Session =>
          users += user
          (true, reason)
      }
    }
    case (false, reason) => (false, reason)
  }

  def insertUser(username: String, password: String): UserValidation = {
    val user = User(None,username,password)
    insertUser(user)
  }

  def validateUser(user: User): UserValidation = {
    val checkedUser = findUserByUsername(user.username)
    if (!checkedUser.isEmpty) {
      logConsole(s"User ${user.username} already exists")
      (false, "User already exists")
    } else {
      user match {
        case User(None, username, password) => {
          if (username.isEmpty() || password.isEmpty()) {
            (false, "Wrong values")
          } else {
            (true, "")
          }
        }
        case _ => (false, "Wrong values")
      }
    }
  }

  type UserValidation = (Boolean, String)

  def getUsersList() = DB.withSession {
    implicit session: Session =>
      users.list

  }

}