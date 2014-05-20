package controllers

import play.mvc.Controller
import securesocial.core.SecureSocial
import play.api.mvc.Action

/**
 * Created by diego on 19/05/14.
 */
object InterestController extends Controller with SecureSocial {
  def getItem(id: Long) = Action {
    implicit request =>
      println(s"getItem $id")
      Ok("true")
  }

  def newItem() = Action {
    implicit request =>
      println("newItem")
      Ok("true")
  }

  def updateItem(id: Long) = TODO
}
