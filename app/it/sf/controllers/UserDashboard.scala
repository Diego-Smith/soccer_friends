package it.sf.controllers

import play.mvc.Controller
import securesocial.core.SecureSocial
import it.sf.service.{InterestService, UserService}


/**
 * Created by diego on 15/05/14.
 */
object UserDashboard extends Controller with SecureSocial with UserService with InterestService {
  def user() = SecuredAction {
    implicit request =>

      val user = findUserByUsername(request.user.email.get).get

      val interests = getUserInterests(user)

      Ok(views.html.user.dashboard(request.user, interests))

  }
}
