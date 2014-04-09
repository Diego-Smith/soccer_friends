package it.sf.manager

import play.api._
import play.api.mvc._
import controllers.routes
import it.sf.controllers.Application

class Util {

}

object Util extends Controller {
  def returnAction = Action {
    Redirect(it.sf.controllers.routes.Application.index)
  }
}