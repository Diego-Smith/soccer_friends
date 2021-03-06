package it.sf.manager

import it.sf.logger.LoggerManager

import it.sf.models.{PageVisited, User}
import play.api.mvc.Request
import play.api.mvc.Session
import it.sf.util.Defines
import scala.collection.mutable

/**
 * Created by diego on 11/04/14.
 */
object SessionManager extends LoggerManager with ComponentRegistry {
  var map = mutable.Map[String, User]()

  //TODO: revisit it
  def logWebPage(webPage: String, request: Request[play.api.mvc.AnyContent]) {
    val idUser: Option[Long] = SessionManager.getUserSession(request.session) match {
      case None => Some(Defines.DEFAULT_USER_ID)
      case o: Option[Object] =>
        o.getOrElse(Defines.DEFAULT_USER_ID) match {
          case u: User => u.id
          case default => Some(Defines.DEFAULT_USER_ID)
        }
      case default => Some(Defines.DEFAULT_USER_ID)
    }
    pageVisitedRepository.insertPageVisited(PageVisited(None, webPage, request.remoteAddress, null, idUser))
  }


  def addUserSession(username: String, hash: String) = {
    val userOption: Option[User] = userService.findUserByUsername(username)
    userOption match {
      case Some(user) => map.put(hash, user)
      case None =>
    }
  }

  def getUserSession(session: Session): Option[User] = {
    val hashCodeOption = session.get(Defines.SESSION_USER_KEY)
    logger.info(s"hashcode: $hashCodeOption \nmap: ${map.toString} \nsession: $session \n")
    hashCodeOption match {
      case None => None
      case Some(hashCode) => map.get(hashCode)
    }
  }
}

