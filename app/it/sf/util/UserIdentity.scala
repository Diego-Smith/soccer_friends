package it.sf.util

import securesocial.core._
import securesocial.core.IdentityId
import securesocial.core.OAuth1Info

/**
 * Created by diego on 20/05/14.
 */
case class UserIdentity(identityId: IdentityId, userId: Long, firstName: String, lastName: String, fullName: String,
                        email: Option[String], avatarUrl: Option[String], authMethod: AuthenticationMethod,
                        oAuth1Info: Option[OAuth1Info],  oAuth2Info: Option[OAuth2Info], passwordInfo: Option[PasswordInfo]) extends Identity
