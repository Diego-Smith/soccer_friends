package it.sf.service

/**
 * Created by diego on 12/05/14.
 */

import play.api.{Logger, Application}
import securesocial.core._
import securesocial.core.PasswordInfo
import securesocial.core.providers.Token
import scala.Some
import play.api.libs.Crypto
import it.sf.models.UserPendentRequest
import it.sf.logger.ApplicationLoggerImpl

class UserServicePluginImpl(application: Application) extends UserServicePlugin(application: Application)
    with UserService with OAuth2Service with UserPendentRequestService with ApplicationLoggerImpl {
  override def deleteExpiredTokens(): Unit = {
    deleteOldPendentRequests
  }

  override def deleteToken(uuid: String): Unit = {
    Logger.debug(s"deleteToken $uuid")
    deletePendentRequest(uuid)
  }

  override def findToken(token: String): Option[Token] = {
    Logger.debug(s"findToken $token")
    val optUPRT: Option[UserPendentRequest] = findPendentRequest(token)
    val optToken: Option[Token] = optUPRT.map(upr => Token(upr.token, upr.email, upr.creationTime, upr.expirationTime, upr.isSignup))
    optToken
  }

  override def save(token: Token): Unit = {
    Logger.debug(s"save $token")
    val optionUser = findUserByUsername(token.email)
    val upr: UserPendentRequest = UserPendentRequest(token.email, token.uuid, token.creationTime, token.expirationTime, !optionUser.isDefined)
    insertPendentRequest(upr)
  }

  override def save(user: Identity): Identity = {
    Logger.debug(s"save $user")

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
        user.passwordInfo match {
          case Some(pi) =>
            updatePassword(username, pi.password)
            Logger.debug(s"updated password for username $username, new password: ${user.passwordInfo}")
          case _ =>
        }
      case None =>
        val password = {
          user.passwordInfo match {
            case Some(pi) => pi.password
            case None => Crypto.sign("")
          }
        }
        val userValidation: UserValidation = insertUser(username, password,
            user.firstName, user.lastName, user.authMethod, user.identityId.providerId)
        Logger.debug(s"${userValidation.result} error ${userValidation.errorMessage}")

        if (userValidation.result) {
          val userId = userValidation.user.get.id.get
          user.authMethod match {
            case AuthenticationMethod.OAuth2 =>
              val oauth2Info: OAuth2Info = user.oAuth2Info.get
              val oauth2InfoTable = it.sf.models.OAuth2Info(userId, oauth2Info.accessToken, oauth2Info.tokenType, oauth2Info.expiresIn, oauth2Info.refreshToken)
              insertOauth2(oauth2InfoTable)
          }
        }
    }
    user

  }

  override def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    logConsole(s"finding email ${email} providerId ${providerId}")
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
    Logger.debug(s"finding id ${id}")
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
