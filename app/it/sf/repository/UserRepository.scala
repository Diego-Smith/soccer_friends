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
//trait UserRepositoryComponent {
//  val userRepository: UserRepository
//}

class UserRepository {
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


  def dbFindUserByUserName(username: String): Option[User] = DB.withSession {
    implicit session: Session =>
      users.filter(_.username === username).firstOption
  }

}