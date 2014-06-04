package it.sf.repository

import scala.slick.lifted
import it.sf.models.UserTable
import it.sf.models.User
import play.api.db.slick.DB
import play.api.db.slick.Session
import scala.slick.lifted.TableQuery
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
/**
 * Created by diego on 21/05/14.
 */
trait UserRepository {
  val users: lifted.TableQuery[UserTable]
  def dbInsertUser(user: User): Long
  def dbFindUserByUsername(username: String): Option[User]
  def dbFindUserByUsername(username: String, password: String): Option[User]
  def dbFindUserById(id: Long): Option[User]
  def dbFindUsers(ids: Seq[Long]): Seq[User]
  def dbUpdatePassword(username: String, password: String)
  def dbGetUsersList() : List[User]
}

class DBUserRepository extends UserRepository {
  val users: lifted.TableQuery[UserTable] = TableQuery[UserTable]
  private val insertReturningId = users returning users.map(_.id)

  def dbInsertUser(user: User): Long = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        insertReturningId += user
      }
    }
  }

  def dbInsertUser2(user: User): Long = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        val insertReturningId2 = users returning users.map(_.id)
        insertReturningId2 += user
      }
    }
  }

  private val invoker = insertReturningId.insertInvoker

  def dbInsertUser3(user: User): Long = {
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

  override def dbFindUserByUsername(username: String): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.username === username).firstOption
  }

  override def dbFindUserByUsername(username: String, password: String): Option[User] =  DB.withSession {
    implicit session: Session =>
      users.filter(user => user.username === username && user.password === password).firstOption
  }

  override def dbFindUserById(id: Long): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.id === id).firstOption
  }

  override def dbFindUsers(ids: Seq[Long]): Seq[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.id inSetBind ids).list
  }

  override def dbUpdatePassword(username: String, password: String): Unit = {
    DB.withSession {
      implicit session: Session =>
        val q = for {
          user <- users
          if user.username === username
        } yield user.password
        q.update(password)
    }
  }

  override def dbGetUsersList(): List[User] = DB.withSession {
    implicit session: Session =>
      users.list
  }
}