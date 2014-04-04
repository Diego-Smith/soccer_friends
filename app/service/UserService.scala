package service
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import models.Users
import models.User

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

  def insertUser(user: User) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        val num = users += user
        num
    }
  }
  
  def getUsersList() = {
     play.api.db.slick.DB.withSession {
      implicit session: Session =>
     	users.list
     	
     }
  }
}