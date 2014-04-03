package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
    val status = Results.Status(200)
    status(views.html.index("Your new application is ready."))
  }
}