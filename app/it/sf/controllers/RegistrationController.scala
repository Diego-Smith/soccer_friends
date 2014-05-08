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
      "username" -> nonEmptyText,
      "password" -> nonEmptyText)(UserData.apply)(UserData.unapply))

  val loginForm: Form[UserData] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText)(UserData.apply)(UserData.unapply)
      verifying("Error username or password", _ match {
      case userData => {
        val userOption: Option[User] = findUserByUsername(userData.username, userData.password)
        userOption.isDefined
      }
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
          val code: String = request.session.hashCode.toString
          SessionManager.addUserSession(form.username, code)
          Redirect(routes.Application.index).withSession(Defines.SESSION_USER_KEY -> code)
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
          val numInsert = insertUser(User(None, form.username, form.password))
          //            Redirect(routes.Application.registration).flashing("success" -> "Utente creato con successo")
          Ok(s"inserted records:  $numInsert")
        })
    }
  }
}

case class UserData(username: String, password: String)