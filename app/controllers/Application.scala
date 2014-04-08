package controllers

import play.api._
import play.api.mvc._
import service.UserService
import models.User
import service.UserService

object Application extends Controller with UserService {

  val logger = Logger

  def index = Action {
    //    Ok(views.html.index("Your new application is ready."))
    val status = Results.Status(200)
    status(views.html.index("Your new application is ready."))
  }

  def insert = Action {
    val user = User(None, "diego", "prova")
    insertUser(user)
    val userList = getUsersList
    Ok(views.html.user.list(userList))
    //    FAKE_RESULT
  }

  def login = TODO

  val FAKE_RESULT = Results.Status(200)

  def helloUser(username: String, password: String) = AuthMe(username, password) {
	  user:User => Ok(s"hello ${user.username}")
  }

  def AuthMe(username: String, password: String)(f: User => Result) = Action {
    logger.info(s"Authing user(username: $username - password: $password)")
    val user: Option[User] = findUserByUsername(username.trim(), password.trim())
    user match {
      case Some(user) => f(user)
      case None => Forbidden("I don't know you")
    }
  }

}