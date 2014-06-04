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
import it.sf.logger.LoggerManager
import it.sf.util.UserIdentity
import it.sf.manager.ComponentRegistry

class UserServicePluginImpl(application: Application) extends UserServicePlugin(application: Application)
    with ComponentRegistry with OAuth2Service with UserPendentRequestService with LoggerManager {
  override def deleteExpiredTokens(): Unit = {
    deleteOldPendentRequests
  }

  override def deleteToken(uuid: String): Unit = {
    logger.info(s"deleteToken $uuid")
    deletePendentRequest(uuid)
  }

  override def findToken(token: String): Option[Token] = {
    logger.info(s"findToken $token")
    val optUPRT: Option[UserPendentRequest] = findPendentRequest(token)
    val optToken: Option[Token] = optUPRT.map(upr => Token(upr.token, upr.email, upr.creationTime, upr.expirationTime, upr.isSignup))
    optToken
  }

  override def save(token: Token): Unit = {
    logger.info(s"save $token")
    val optionUser = userService.findUserByUsername(token.email)
    val upr: UserPendentRequest = UserPendentRequest(token.email, token.uuid, token.creationTime, token.expirationTime, !optionUser.isDefined)
    insertPendentRequest(upr)
  }

  override def save(user: Identity): Identity = {
    logger.info(s"save $user")

    val username = {
      user.identityId match {
        case IdentityId(_, "userpass") =>
          user.identityId.userId
        case _ =>
          user.identityId.userId + user.identityId.providerId
      }
    }

    val optionUser = userService.findUserByUsername(username)

    optionUser match {
      case Some(u) =>

        user.passwordInfo match {
          case Some(pi) =>
            userService.updatePassword(username, pi.password)
            logger.info(s"updated password for username $username, new password: ${user.passwordInfo}")

          case _ =>
        }

        user.authMethod match {
          case AuthenticationMethod.OAuth2 =>
            val oauth2Info: OAuth2Info = user.oAuth2Info.get

            val oauth2InfoTable = it.sf.models.OAuth2Info(u.id.get, oauth2Info.accessToken, oauth2Info.tokenType, oauth2Info.expiresIn, oauth2Info.refreshToken)
            updateOauth2(oauth2InfoTable)
        }

      case None =>
        val password = {
          user.passwordInfo match {
            case Some(pi) => pi.password
            case None => Crypto.sign("")
          }
        }
        val userValidation: UserValidation = userService.insertUser(username, password,
            user.firstName, user.lastName, user.authMethod, user.identityId.providerId)
        logger.info(s"${userValidation.result} error ${userValidation.errorMessage}")

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
    logger.info(s"finding email $email providerId $providerId")
    val optionUser = {
      providerId match {
        case "userpass" => userService.findUserByUsername(email)
        case _ => userService.findUserByUsername(email + providerId)
      }
    }

    optionUser match {
      case Some(user) =>
        val pass: PasswordInfo = PasswordInfo.apply("cry", user.password, None)
        val ui: UserIdentity = UserIdentity(IdentityId(email, providerId), user.id.get, user.name.getOrElse(""), user.surname.getOrElse(""),
        s"${user.name.getOrElse("")} ${user.surname.getOrElse("")}", Some(user.username), None, user.getAuthenticationMethod, None, None, Some(pass))
        Some(ui)
      case None => None
    }
  }

  override def find(id: IdentityId): Option[Identity] = {
    logger.info(s"finding id $id")
    val optionUser = {
      id match {
        case IdentityId(_, "userpass") =>
          userService.findUserByUsername(id.userId)
        case _ =>
          userService.findUserByUsername(id.userId + id.providerId)
      }
    }

    optionUser match {
      case Some(user) =>
        val pass: PasswordInfo = PasswordInfo.apply("cry", user.password, None)
        val ui: UserIdentity = UserIdentity(id, user.id.get, user.name.getOrElse(""), user.surname.getOrElse(""),
          s"${user.name.getOrElse("")} ${user.surname.getOrElse("")}", Some(user.username), None, user.getAuthenticationMethod, None, None, Some(pass))
        Some(ui)
      case None => None
    }

  }
}
