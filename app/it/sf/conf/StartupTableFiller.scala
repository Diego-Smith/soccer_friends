package it.sf.conf

import it.sf.logger.LoggerManager
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import play.api.db.DB
import securesocial.core.AuthenticationMethod
import play.api.libs.Crypto
import scala.Some
import it.sf.models.OAuth2Info
import it.sf.models.Category
import it.sf.manager.ComponentRegistry

object StartupTableFiller extends LoggerManager with ComponentRegistry {
  def obtainDB: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    obtainDB

    val dbAlreadyFilledOption: Option[Configuration] = configurationRepository.findConfigurationByKey("DB_SETUP")

    dbAlreadyFilledOption match {
      case Some(dbAlreadyFilled) => logger.info("RELOAD - DETECTED TABLES ALREADY FILLED")
      case _ =>
        fillCategories
        fillUserTable
        fillFriendship
        configurationRepository.insertConfiguration("DB_SETUP", "CREATE THIS TO PREVENT OTHER INSERTS ON RELOAD")
    }

  }

  def fillUserTable() = {
    userService.insertUser("user1", Crypto.sign("user1"), "User", "1", AuthenticationMethod.UserPassword, "userpass")
    userService.insertUser("diego", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    userService.insertUser("diego.naali@gmail.com", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    userService.insertUser("10203749685571466facebook", Crypto.sign("password"), "Diego", "Fabbro", AuthenticationMethod.OAuth2, "facebook")
    oAuth2Repository.insertOrUpdateOauth2(OAuth2Info(4, "thisTokenWillBeChange", None, Some(5108366), None))


    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        interestRepository.interests += Interest(None, "fantasy", 4, Some(1))
        interestRepository.interests += Interest(None, "horror", 4, Some(1))
        interestRepository.interests += Interest(None, "football", 4, Some(3))

      }
    }
    userInterestRepository.insertUserInterest(4, 1)
    userInterestRepository.insertUserInterest(4, 2)
    userInterestRepository.insertUserInterest(4, 3)
    userInterestRepository.insertUserInterest(2, 1)
  }

  def fillCategories() {


    categoryRepository.insertCategory(Category(None, "book"))
    categoryRepository.insertCategory(Category(None, "school"))
    categoryRepository.insertCategory(Category(None, "sport"))
  }

  def fillFriendship() {
    //    val friendshipService = new FriendshipService {}
    //    insertFriendship(1,2)
    //    insertFriendship(Friendship(None, 2, 1))
  }
}