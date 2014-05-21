package it.sf.manager

import it.sf.service.UserServiceComponent
import it.sf.repository.{UserRepository, UserRepositoryComponent}

/**
 * Created by diego on 21/05/14.
 */
trait ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  override val userRepository = new UserRepository {}
  override val userService = new UserService
}