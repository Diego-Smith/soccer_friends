package it.external.tryout

/**
 * Created by diego on 04/06/14.
 */
class TestDI {

}

trait UserServiceTest {
  def test: List[String]
}

trait UserServiceTestImpl1 extends UserServiceTest {
  override def test: List[String] = {
    println("UserServiceTestImpl1")
    List("test")
  }
}
trait UserServiceTestImpl2 extends UserServiceTest {
  override def test: List[String] = {
    println("UserServiceTestImpl2")
    List("2")
  }
}

trait ProvideService {
  userService: UserServiceTest with DBService =>
  def getList() = {
    userService.test
  }
}

object RunTest {
  val myApp = new ProvideService with UserServiceTestImpl1 with DBServiceImpl
  val myApp2 = new ProvideService with UserServiceTestImpl2 with DBServiceImpl
  def getList() = {
    myApp.db
    myApp.test
  }
  def getList2(): List[String]= {
    myApp2.db
    myApp2.test
  }
}


trait DBService {
  def db
}
trait DBServiceImpl extends DBService{
  def db = {
    println("queeeeeeeeeery")
  }
}