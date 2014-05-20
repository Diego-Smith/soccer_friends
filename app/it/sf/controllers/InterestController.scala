package controllers

import play.mvc.Controller
import securesocial.core.SecureSocial
import play.api.mvc.Action
import it.sf.service.InterestService

/**
 * Created by diego on 19/05/14.
 */
object InterestController extends Controller with SecureSocial with InterestService {
  def getItem(id: Long) = Action {
    implicit request =>
      println(s"getItem $id")
      Ok("true")
  }

  def newItem(name: String) = SecuredAction {
    implicit request =>
      println("newItem")
      Ok("true")
  }

  def updateItem(id: Long) = TODO
}
