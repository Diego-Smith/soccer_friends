package it.sf.service

/**
 * Created by diego on 12/05/14.
 */

import play.api.Application
import securesocial.core._
import securesocial.core.PasswordInfo
import securesocial.core.providers.Token
import scala.Some
import play.api.libs.Crypto
import it.sf.models.UserPendentRequest

class UserServicePluginImpl(application: Application) extends UserServicePlugin(application: Application) with UserService with OAuth2Service with UserPendentRequestService {
  override def deleteExpiredTokens(): Unit = {
    deleteOldPendentRequests
  }

  override def deleteToken(uuid: String): Unit = {
        println(s"deleteToken $uuid")
    deletePendentRequest(uuid)
  }

  override def findToken(token: String): Option[Token] = {
        println(s"findToken $token")
    val optUPRT: Option[UserPendentRequest] = findPendentRequest(token)
    val optToken: Option[Token] = optUPRT.map(upr => Token(upr.token, upr.email, upr.creationTime, upr.expirationTime, upr.isSignup))
    //    println(optToken)
    //    println(s"is expired? ${optToken.get.isExpired}")
    optToken
  }

  override def save(token: Token): Unit = {
    println(s"save $token")
    val optionUser = findUserByUsername(token.email)
    val upr: UserPendentRequest = UserPendentRequest(token.email, token.uuid, token.creationTime, token.expirationTime, ! optionUser.isDefined)
    insertPendentRequest(upr)
  }

  override def save(user: Identity): Identity = {
        println(s"save $user")

    val username = {
      user.identityId match {
        case IdentityId(_, "userpass") =>
          user.identityId.userId
        case _ =>
          user.identityId.userId + user.identityId.providerId
      }
    }

    val optionUser = findUserByUsername(username)

    optionUser match {
      case Some(u) =>
        updatePassword(username, user.passwordInfo.get.password)
        user
      case None =>
        val userValidation: UserValidation = insertUser(username, user.passwordInfo.getOrElse(PasswordInfo.apply("cry", Crypto.sign("password"), None)).password,
          user.firstName, user.lastName, user.authMethod, user.identityId.providerId)
        println(s"${userValidation.result} error ${userValidation.errorMessage}")

        if (userValidation.result) {
          val userId = userValidation.user.get.id.get
          user.authMethod match {
            case AuthenticationMethod.OAuth2 =>
              val oauth2Info: OAuth2Info = user.oAuth2Info.get

              val oauth2InfoTable = it.sf.models.OAuth2Info(userId, oauth2Info.accessToken, oauth2Info.tokenType, oauth2Info.expiresIn, oauth2Info.refreshToken)
              insertOauth2(oauth2InfoTable)

              val pass: PasswordInfo = PasswordInfo.apply("cry", Crypto.sign("password"), None)
              SocialUser(user).copy(passwordInfo = Some(pass))
            case _ => user
          }
        } else {
          user
        }
    }

  }

  override def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    val optionUser = {
      providerId match {
        case "userpass" => findUserByUsername(email)
        case _ => findUserByUsername(email + providerId)
      }
    }

    optionUser match {
      case Some(user) =>
        val pass: PasswordInfo = PasswordInfo.apply("cry", user.password, None)
        val socialUser: SocialUser = SocialUser.apply(IdentityId(email, providerId), user.name.getOrElse(""), user.surname.getOrElse(""),
          s"${user.name.getOrElse("")} ${user.surname.getOrElse("")}", Some(user.username), None, user.getAuthenticationMethod, None, None, Some(pass))
        Some(socialUser)
      case None => None
    }
  }

  override def find(id: IdentityId): Option[Identity] = {
    val optionUser = {
      id match {
        case IdentityId(_, "userpass") =>
          findUserByUsername(id.userId)
        case _ =>
          findUserByUsername(id.userId + id.providerId)
      }
    }

    optionUser match {
      case Some(user) =>
        val pass: PasswordInfo = PasswordInfo.apply("cry", user.password, None)
        val socialUser: SocialUser = SocialUser.apply(id, user.name.getOrElse(""), user.surname.getOrElse(""),
          s"${user.name.getOrElse("")} ${user.surname.getOrElse("")}", Some(user.username), None, user.getAuthenticationMethod, None, None, Some(pass))
        Some(socialUser)
      case None => None
    }

  }
}
