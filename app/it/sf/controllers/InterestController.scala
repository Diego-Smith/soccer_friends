package controllers

import play.mvc.Controller
import securesocial.core.{Identity, SecureSocial}
import play.api.mvc.Action
import it.sf.service.InterestService
import it.sf.util.UserIdentity
import it.sf.models.Interest
import it.sf.logger.ApplicationLoggerImpl

/**
 * Created by diego on 19/05/14.
 */
object InterestController extends Controller with SecureSocial with InterestService with ApplicationLoggerImpl {
  def getItem(id: Long) = Action {
    implicit request =>
      println(s"getItem $id")
      Ok("true")
  }

  def newItem(name: String) = SecuredAction {
    implicit request => {
      val user: Identity = request.user
      val userIdentity: UserIdentity = user.asInstanceOf[UserIdentity]

      val interestOpt: Option[Interest] = checkUserInterest(name, userIdentity.userId)
      interestOpt match {
        case Some(interest) => Ok("false")
        case None => {
          insertInterestToUser(name, userIdentity.userId)
          Ok("true")
        }
      }

    }

  }

  def interestList() = SecuredAction {
    implicit  request => {
//      logConsole("interestList")
      val userIdentity: UserIdentity = request.user.asInstanceOf[UserIdentity]
      val userInterests: List[Interest] = getUserInterests(userIdentity.userId)
      Ok(views.html.interests.list(userInterests))
    }
  }

  def updateItem(id: Long) = TODO
}
