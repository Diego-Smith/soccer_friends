package it.sf.manager

import it.sf.logger.ApplicationLoggerImpl

import it.sf.models.{PageVisited, User}
import play.api.mvc.Request
import play.api.mvc.Session
import it.sf.util.Defines
import it.sf.service.{PageVisitedService, UserService}
import scala.collection.mutable

/**
 * Created by diego on 11/04/14.
 */
object SessionManager extends ApplicationLoggerImpl with UserService with PageVisitedService {
  var map = mutable.Map[String, User]()

  //TODO: revisit it
  def logWebPage(webPage: String, request: Request[play.api.mvc.AnyContent]) {
    val idUser: Option[Int] = SessionManager.getUserSession(request.session) match {
      case None => Some(Defines.DEFAULT_USER_ID)
      case o: Option[Object] =>
        o.getOrElse(Defines.DEFAULT_USER_ID) match {
          case u: User => u.id
          case default => Some(Defines.DEFAULT_USER_ID)
        }
      case default => Some(Defines.DEFAULT_USER_ID)
    }
    insertPageVisited(PageVisited(None, webPage, request.remoteAddress, null, idUser))
  }


  def addUserSession(username: String, hash: String) = {
    val userOption: Option[User] = findUserByUsername(username)
    userOption match {
      case Some(user) => map.put(hash, user)
      case None =>
    }
  }

  def getUserSession(session: Session): Option[User] = {
    val hashCodeOption = session.get(Defines.SESSION_USER_KEY)
    logConsole(s"hashcode: $hashCodeOption \nmap: ${map.toString} \nsession: $session \n")
    hashCodeOption match {
      case None => None
      case Some(hashCode) => map.get(hashCode)
    }
  }
}

