package controllers

import play.mvc.Controller
import securesocial.core.{Identity, SecureSocial}
import play.api.mvc.Action
import it.sf.util.UserIdentity
import it.sf.models.Interest
import it.sf.manager.ComponentRegistry

/**
 * Created by diego on 19/05/14.
 */
object InterestController extends Controller with SecureSocial with ComponentRegistry {
  def getItem(id: Long) = Action {
    implicit request =>
      println(s"getItem $id")
      Ok("true")
  }

  def newItem(name: String) = SecuredAction {
    implicit request => {
      val user: Identity = request.user
      val userIdentity: UserIdentity = user.asInstanceOf[UserIdentity]

      val interestOpt: Option[Interest] = interestRepository.checkUserInterest(name, userIdentity.userId)
      interestOpt match {
        case Some(interest) => Ok("false")
        case None =>
          interestRepository.insertInterestToUser(name, userIdentity.userId)
          Ok("true")
      }

    }

  }

  def interestList() = SecuredAction {
    implicit  request => {
//      logConsole("interestList")
      val userIdentity: UserIdentity = request.user.asInstanceOf[UserIdentity]
      val userInterests: List[Interest] = interestRepository.getUserInterests(userIdentity.userId)
      Ok(views.html.interests.list(userInterests))
    }
  }

  def updateItem(id: Long) = TODO
}
