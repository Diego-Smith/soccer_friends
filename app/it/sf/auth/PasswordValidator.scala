package it.sf.auth

import securesocial.core.providers.utils.{PasswordHasher, PasswordValidator}
import play.api.Application
import securesocial.core.PasswordInfo
import play.api.libs.Crypto
import play.Logger

/**
 * Created by diego on 08/05/14.
 */
class SocialPasswordValidator(application: Application) extends PasswordValidator {


  override def errorMessage: String = {
    "Not valid password"
  }

  override def isValid(password: String): Boolean = {
    if(password.length < 8) {
      false
    } else {
      true
    }
  }
}



class SocialPasswordHasher(application: Application) extends PasswordHasher {
  override def id: String = "cry"

  override def matches(passwordInfo: PasswordInfo, suppliedPassword: String): Boolean = {
    Logger.debug(s"suppl password: $suppliedPassword crypted ${Crypto.sign(suppliedPassword)} and must equal to ${passwordInfo.password}")

    Crypto.sign(suppliedPassword).equals(passwordInfo.password)

  }

  override def hash(plainPassword: String): PasswordInfo = {
    Logger.debug(s"hashing $plainPassword")
    PasswordInfo.apply("cry", Crypto.sign(plainPassword), None)
  }
}