package it.sf.controllers

import play.api._
import play.api.mvc._
import it.sf.service.UserService
import it.sf.models.User
import securesocial.core.java.SecureSocial.SecuredAction
import securesocial.core.{SecuredRequest, SecureSocial}
import it.sf.util.WithProvider

object Application extends Controller with UserService with SecureSocial {

  val logger = Logger

  def index = SecuredAction {
    implicit request =>
      Ok(views.html.index("Your new application is ready.", request.user))
  }

  //TODO: remove
  def insert = Action {
    //    FAKE_RESULT
    val user = User(None, "diego", "test")
    insertUser(user)
    val userList = getUsersList
    Ok(views.html.user.list(userList))
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