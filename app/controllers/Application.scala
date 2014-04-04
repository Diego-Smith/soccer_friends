package controllers

import play.api._
import play.api.mvc._
import service.UserService
import models.User
import service.UserService

object Application extends Controller with UserService {

  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
    val status = Results.Status(200)
    status(views.html.index("Your new application is ready."))
  }
  
  def insert = Action {
	val user = User(None, "diego", "prova")
    insertUser(user)
    val userList = getUsersList
    Ok(views.html.user.list(userList))
//    FAKE_RESULT
  }
  
  val FAKE_RESULT = Results.Status(200)
}