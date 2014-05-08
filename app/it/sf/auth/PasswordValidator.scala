package it.sf.auth

import securesocial.core.providers.utils.{PasswordHasher, PasswordValidator}
import play.api.Application
import securesocial.core.PasswordInfo

/**
 * Created by diego on 08/05/14.
 */
class SocialPasswordValidator(application: Application) extends PasswordValidator{


  override def errorMessage: String = {
    "ahuz"
  }

  override def isValid(password: String): Boolean = {
    println(password)
    true
  }
}



class SocialPasswordHasher(application: Application) extends PasswordHasher {
  override def id: String = "md5"

  override def matches(passwordInfo: PasswordInfo, suppliedPassword: String): Boolean = {
    println(s"passwordInfo $passwordInfo and suppliedPassword $suppliedPassword")
    true
  }

  override def hash(plainPassword: String): PasswordInfo = PasswordInfo.apply("md5", plainPassword, None)
}