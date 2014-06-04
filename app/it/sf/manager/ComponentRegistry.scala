package it.sf.manager

import it.sf.service.UserService
import it.sf.repository.{UserRepositoryInterface, UserRepository}

/**
 * Created by diego on 21/05/14.
 */
trait ComponentRegistry {
  import com.softwaremill.macwire.MacwireMacros._
  lazy val userRepository: UserRepositoryInterface = wire[UserRepository]
  lazy val userService = wire[UserService]
}