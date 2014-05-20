package controllers

import play.api.mvc.{Action, Result, Controller, Results}
import it.sf.service.UserService
import it.sf.models.User
import securesocial.core.SecureSocial
import play.api.{Routes, Logger}

object Application extends Controller with UserService with SecureSocial {

  val logger = Logger

  //TODO: try to auth in facebook only if it's connected
  def index = UserAwareAction {
    implicit request =>
      request.user match {
        case Some(i) => Ok(views.html.index(request.user))
        case None => Redirect("/authenticate/facebook")
      }

  }

  //TODO: remove
  def AuthMe(username: String, password: String)(f: User => Result) = Action {
    logger.info(s"Authenticating user(username: $username - password: $password)")
    val user: Option[User] = findUserByUsername(username.trim(), password.trim())
    user match {
      case Some(someUser) => f(someUser)
      case None => Forbidden("I don't know you")
    }
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.InterestController.getItem,
        routes.javascript.InterestController.newItem,
        routes.javascript.InterestController.updateItem,
        routes.javascript.InterestController.interestList
      )
    )
  }
}