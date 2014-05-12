package it.sf.service

/**
 * Created by diego on 12/05/14.
 */

import play.api.Application
import securesocial.core._
import securesocial.core.providers.Token
import it.sf.models.User
import it.sf.models.User
import securesocial.core.providers.Token
import scala.Some
import it.sf.models.User
import securesocial.core.IdentityId
import securesocial.core.PasswordInfo
import securesocial.core.providers.Token
import scala.Some

class UserServicePluginImpl(application: Application) extends UserServicePlugin(application: Application) with UserService with OAuth2Service {
  override def deleteExpiredTokens(): Unit = {
    println("deleteExpireTokens")
  }

  override def deleteToken(uuid: String): Unit = {
    println(s"deleteToken $uuid")
  }

  override def findToken(token: String): Option[Token] = {
    println("findToken")
    None
  }

  override def save(token: Token): Unit = {
    println(s"save token $token")
  }

  override def save(user: Identity): Identity = {

    val optionUser: Option[User] = findUserByUsername(user.identityId.userId + user.identityId.providerId)

    optionUser match {
      case Some(user) => {
        println("already exists")
      }
      case None => {
        val username: String = user.identityId.userId + user.identityId.providerId

        val userValidation: UserValidation = insertUser(username, "password", user.firstName, user.lastName, user.authMethod, user.identityId.providerId)
        println(s"${userValidation.result} error ${userValidation.errorMessage}")

        if (userValidation.result) {
          val userId = userValidation.user.get.id.get
          user.authMethod match {
            case AuthenticationMethod.OAuth2 => {
              val oauth2Info: OAuth2Info = user.oAuth2Info.get

              val oauth2InfoTable = it.sf.models.OAuth2Info(userId, oauth2Info.accessToken, oauth2Info.tokenType, oauth2Info.expiresIn, oauth2Info.refreshToken)
              insertOauth2(oauth2InfoTable)
            }
          }
        }

      }
    }

    val pass: PasswordInfo = PasswordInfo.apply("md5", "password", None)
    SocialUser(user).copy(passwordInfo = Some(pass))
  }

  override def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    println(s"find $email $providerId")
    None
  }

  override def find(id: IdentityId): Option[Identity] = {
    println(s"findiiing $id")
    val optionUser: Option[User] = findUserByUsername(id.userId + id.providerId)

    optionUser match {
      case Some(user) => {
        val pass: PasswordInfo = PasswordInfo.apply("md5", user.password, None)
        val socialUser: SocialUser = SocialUser.apply(id, user.name.getOrElse(""), user.surname.getOrElse(""),
          user.name.getOrElse("") + user.surname.getOrElse(""), Some(user.username), None, user.getAuthenticationMethod, None, None, Some(pass))
        Some(socialUser)
      }
      case None => None
    }

  }
}
