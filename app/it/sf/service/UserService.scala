package it.sf.service
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.Users
import it.sf.models.User
import play.api.db.slick._

trait UserService {
  val users = TableQuery[Users]

  def doSomething(id: Int) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        println("test")
        //        users += User("John Doe")
        //        users += User("Fred Smith")

        // print the users (select * from USERS)
        println(users.list)
    }
    // insert two User instances

  }

  def findUserByUsername(username: String, password: String): Option[User] = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        users.filter(user => user.username === username && user.password === password).firstOption
    }
  }
  
    def findUserByUsername(username: String): Option[User] = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        users.filter(_.username === username).firstOption
    }
  }

  def insertUser(user: User): Integer = {
    validateUser(user) match {
      case (true, _) => {
    	  play.api.db.slick.DB.withSession {
    		  implicit session: Session =>
    	  users += user
    	  }
      }
      case _ => 0
    }
  }
  
  def validateUser(user: User) : UserValidation = {
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

  type UserValidation = (Boolean, String)
  
  def getUsersList() = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        users.list

    }
  }
  
}