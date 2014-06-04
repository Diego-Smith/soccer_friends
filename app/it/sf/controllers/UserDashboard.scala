package it.sf.controllers

import play.mvc.Controller
import securesocial.core.SecureSocial
import it.sf.util.UserIdentity
import it.sf.manager.ComponentRegistry


/**
 * Created by diego on 15/05/14.
 */
object UserDashboard extends Controller with SecureSocial with ComponentRegistry {
  def user() = SecuredAction {
    implicit request =>

//      val user = findUserByUsername(request.user.email.get).get

      val ui: UserIdentity = request.user.asInstanceOf[UserIdentity]

      val interests = interestRepository.getUserInterests(ui.userId)

      Ok(views.html.user.dashboard(request.user, interests))

  }
}
