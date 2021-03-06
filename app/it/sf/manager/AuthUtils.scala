package it.sf.manager

import play.api.mvc.RequestHeader
import it.sf.models.User

object AuthUtils extends ComponentRegistry {
  def parseUserFromCookie(implicit request: RequestHeader) = request.session.get("username").flatMap(username => userService.findUserByUsername(username))

  def parseUserFromQueryString(implicit request: RequestHeader) = {
    val query = request.queryString.map {
      case (k, v) => k -> v.mkString
    }
    val username = query get "username"
    val password = query get "password"

    (username, password) match {
      case (Some(u), Some(p)) => userService.findUserByUsername(u, p)
      case _ => None
    }
  }

  def parseUserFromRequest(implicit request: RequestHeader): Option[User] = {
    parseUserFromQueryString orElse parseUserFromCookie
  }
}
