package it.sf.controllers

import play.mvc.Controller
import securesocial.core.SecureSocial


/**
 * Created by diego on 15/05/14.
 */
object UserDashboard extends Controller with SecureSocial {
  def user() = SecuredAction {
    implicit request =>



      Ok(views.html.user.dashboard(request.user))

  }
}
