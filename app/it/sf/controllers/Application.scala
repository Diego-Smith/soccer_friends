package it.sf.controllers

import play.api._
import play.api.mvc._
import it.sf.service.UserService
import it.sf.models.User

object Application extends Controller with UserService {

  val logger = Logger

  def index = Action {
    implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  //TODO: remove
  def insert = Action {
    insertUser(user)
    val userList = getUsersList
    Ok(views.html.user.list(userList))
    //    FAKE_RESULT
    val user = User(None, "diego", "test")
  }

  //TODO: remove
  val FAKE_RESULT = Results.Status(200)

  //TODO: remove
  def helloUser(username: String, password: String) = AuthMe(username, password) {
    user: User => Ok(s"hello ${user.username}")
  }

  //TODO: remove??
  def AuthMe(username: String, password: String)(f: User => Result) = Action {
    logger.info(s"Authenticating user(username: $username - password: $password)")
    val user: Option[User] = findUserByUsername(username.trim(), password.trim())
    user match {
      case Some(someUser) => f(someUser)
      case None => Forbidden("I don't know you")
    }
  }
}