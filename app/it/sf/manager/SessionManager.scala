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

//  private def addSession(key: String, value: Object) = {
//    key match {
//      case null =>
//        null
//      case a =>
//        //        val session = Http.Context.current().session()
//        map.put(key, value)
//    }
//  }

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

//  def addUserSession(id: Long, user: User) = {
//    map.put(getUserKey(id), user)
//  }

  def addUserSession(username: String, hash: String) = {
    val user: Option[User] = findUserByUsername(username)
    user match {
      case Some(user) => map.put(hash, user)
      case None =>
    }
  }

  private def getUserKey(idUser: String): String = {
    Defines.SESSION_USER_KEY + idUser
  }

  private def getUserKey(idUser: Long): String = {
    getUserKey(idUser.toString)
  }

  def getUserSession(session: Session): Option[User] = {
    val hashcode = session.get(Defines.SESSION_USER_KEY)
    logConsole(s"hashcode: $hashcode \nmap: ${map.toString} \nsession: $session \n")
    hashcode match {
      case None => None
      case Some(code) => map.get(code)
    }
  }

  private def get(key: String): Option[Object] = {
    map.get(key)
  }
}

