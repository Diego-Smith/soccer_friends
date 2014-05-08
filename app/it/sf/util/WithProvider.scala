package it.sf.util

import securesocial.core.{Identity, Authorization}

/**
 * Created by diego on 08/05/14.
 */
case class WithProvider(provider: String) extends Authorization {
  def isAuthorized(user: Identity) = {
    user.identityId.providerId == provider
  }
}