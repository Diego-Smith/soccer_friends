package it.sf.controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import views.html.helper.inputPassword
import play.api._
import play.api.mvc._
import it.sf.service.UserService
import it.sf.models.User

object RegistrationController extends Controller with UserService {
  val userForm: Form[UserData] = Form(
    mapping(
      "name" -> nonEmptyText,
      "password" -> nonEmptyText)(UserData.apply)(UserData.unapply))

      
//  val userForm: Form[(String, String)] = Form(
//    tuple("name" -> text,
//      "password" -> text) verifying ("Error", _ match {
//        case (name, password) => !(name.isEmpty() || password.isEmpty())
//      }) 
//   ) 

  def register() = {
    Action {
      Ok(views.html.registration.registration(userForm))
    }
  }

  def submitLogin() = Action { implicit request =>
    {
      userForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.registration.registration(formWithErrors)),
        form => {
          val numInsert = insertUser(User(None, form.name, form.password))
          //            Redirect(routes.Application.registration).flashing("success" -> "Utente creato con successo")
          Ok(s"inserted records:  $numInsert")
        })
    }
  }
}

case class UserData(name: String, password: String)