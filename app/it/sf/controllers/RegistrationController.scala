package it.sf.controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import it.sf.service.UserService
import it.sf.models.User
import it.sf.util.Defines
import it.sf.manager.SessionManager

object RegistrationController extends Controller with UserService {
  val userForm: Form[UserData] = Form(
    mapping(
      "name" -> nonEmptyText,
      "password" -> nonEmptyText)(UserData.apply)(UserData.unapply))

  //  val loginForm: Form[UserData] = Form(
  //    tuple("name" -> text,
  //      "password" -> text) verifying("Error", _ match {
  //      case (name, password) => findUserByUsername(name, password).isDefined
  //    })
  //  )

  val loginForm: Form[UserData] = Form(
    mapping(
      "name" -> nonEmptyText,
      "password" -> nonEmptyText)(UserData.apply)(UserData.unapply)
      verifying("Error username or password", _ match {
      case userData => findUserByUsername(userData.name, userData.password).isDefined
    })
  )

  def login = Action {
    Ok(views.html.registration.registration(loginForm, "login"))
  }

  def postLogin = Action {
    implicit request => {
      loginForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.registration.registration(formWithErrors, "login")),
        form => {
          SessionManager.addUserSession(form.name, request.session.hashCode.toString)
          Ok(s"Logged!").withSession(Defines.SESSION_USER_KEY -> request.session.hashCode.toString)
        })
    }
  }

  def register() = Action {
    Ok(views.html.registration.registration(userForm, "register"))
  }

  def postRegister(): Action[AnyContent] = Action {
    implicit request => {
      userForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.registration.registration(formWithErrors, "register")),
        form => {
          val numInsert = insertUser(User(None, form.name, form.password))
          //            Redirect(routes.Application.registration).flashing("success" -> "Utente creato con successo")
          Ok(s"inserted records:  $numInsert")
        })
    }
  }
}

case class UserData(name: String, password: String)