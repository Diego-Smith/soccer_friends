package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.Config.driver.simple.Tag
import securesocial.core.AuthenticationMethod
import it.sf.models.AuthenticationMethodEnum.AuthenticationMethodEnum

case class User(id: Option[Int] = None, username: String, password: String, name: Option[String], surname: Option[String], authMethod: String, providerId: String) {
  def getAuthenticationMethod : AuthenticationMethod = {
    AuthenticationMethodEnum.withName(authMethod).asInstanceOf[AuthenticationMethodEnum].authenticationMethod
  }

  def getProviderEnum: ProviderIdEnum.Value = {
      providerId.asInstanceOf
  }
}

class UserTable(tag: Tag) extends Table[User](tag, "USER") {
  // Auto Increment the id primary key column
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  // The name can't be null
  def username = column[String]("USERNAME", O.NotNull)
  def password = column[String]("PASSWORD", O.NotNull)
  def name = column[String]("NAME", O.Nullable)
  def surname = column[String]("SURNAME", O.Nullable)
  def authMethod = column[String]("AUTH_METHOD", O.NotNull)
  def providerId = column[String]("PROVIDER_ID", O.NotNull)

  // the * projection (e.g. select * ...) auto-transform the tupled column values to / from a User
  def * = (id.?, username, password, name.?, surname.?, authMethod, providerId) <> (User.tupled, User.unapply)
  def usernameUnique = index("UNIQUE_USERNAME", username, unique = true)
}

object AuthenticationMethodEnum extends Enumeration {

  type AuthenticationMethodEnum = AuthMethodValue

  case class AuthMethodValue(controlText: String, authenticationMethod: AuthenticationMethod) extends Val(controlText)

  val OAuth1 = AuthMethodInternalValue("OAuth1", AuthenticationMethod.OAuth1)
  val OAuth2 = AuthMethodInternalValue("OAuth2", AuthenticationMethod.OAuth2)
  val UserPassword = AuthMethodInternalValue("UserPassword", AuthenticationMethod.UserPassword)
  val OpenID = AuthMethodInternalValue("OpenID", AuthenticationMethod.OpenId)

  def getValueByAuthenticationMethod(authenticationMethod: AuthenticationMethod) : String = {
    AuthenticationMethodEnum.values.filter(_.asInstanceOf[AuthenticationMethodEnum].authenticationMethod.equals(authenticationMethod))
      .head.asInstanceOf[AuthenticationMethodEnum].controlText
  }

  protected final def AuthMethodInternalValue(controlText: String, authenticationMethod: AuthenticationMethod): AuthMethodValue = {
    AuthMethodValue(controlText, authenticationMethod)
  }
}

object ProviderIdEnum extends Enumeration {
  type ProviderIdEnum = Value
  val UserPassword, Twitter, facebook = Value
}