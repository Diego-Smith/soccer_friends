package it.sf.manager

import it.sf.service.UserService
import it.sf.repository._

/**
 * Created by diego on 21/05/14.
 */
trait ComponentRegistry {
  import com.softwaremill.macwire.MacwireMacros._
  lazy val userRepository: UserRepository = wire[DBUserRepository]
  lazy val userService = wire[UserService]
  lazy val categoryRepository: CategoryRepository = wire[CategoryRepository]
  lazy val configurationRepository: ConfigurationRepository = wire[ConfigurationRepository]
  lazy val friendshipRepository = wire[FriendshipRepository]
  lazy val interestRepository = wire[InterestRepository]
  lazy val oAuth2Repository = wire[OAuth2Repository]
  lazy val pageVisitedRepository = wire[PageVisitedRepository]
  lazy val userInterestRepository = wire[UserInterestRepository]
  lazy val userPendentRequestRepository = wire[UserPendentRequestRepository]
}