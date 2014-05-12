package services

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.ScalaCheck
import play.api.test.WithApplication
import it.sf.service.UserRepository
import it.sf.models.User

/**
 * Created by diego on 09/05/14.
 */

@RunWith(classOf[JUnitRunner])
class PerformanceSlickTest extends Specification with ScalaCheck {

  "Check performance Compile?" should {


    "Performance with val" in new WithApplication() {

      private val userRepository: UserRepository with Object = new UserRepository {}
      //case class User(id: Option[Int] = None, username: String, password: String, name: Option[String], surname: Option[String], authMethod: String, providerId: String) {
      (1 to 40000).foreach(intVal => {
        val user: User = User(None, s"user1$intVal", "user1", Some("diego"), Some("diego"), "test", "test")
        userRepository.dbInsertUser4(user)
      })
    }


    }

//  "Check performance not compile?" should {
//    "perfoooo2" in new WithApplication() {
//
//      private val userRepository: UserRepository with Object = new UserRepository {}
//      //case class User(id: Option[Int] = None, username: String, password: String, name: Option[String], surname: Option[String], authMethod: String, providerId: String) {
//      (1 to 40000).foreach(intVal => {
//        val user: User = User(None, s"user1$intVal", "user1", Some("diego"), Some("diego"), "test", "test")
//        userRepository.dbInsertUser2(user)
//      })
//    }
//
//  }

}
