package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import play.api.db.DB
import it.sf.service._
import securesocial.core.AuthenticationMethod
import play.api.libs.Crypto
import scala.Some
import it.sf.models.OAuth2Info
import it.sf.models.Category
import it.sf.models.UserInterest.Configuration

object StartupTableFiller extends ApplicationLoggerImpl with UserService with CategoryService with FriendshipService with OAuth2Service with InterestService
with UserInterestService with ConfigurationService {
  def obtainDB: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    obtainDB

    val dbAlreadyFilledOption: Option[Configuration] = findConfigurationByKey("DB_SETUP")

    dbAlreadyFilledOption match {
      case Some(dbAlreadyFilled) => logConsole("RELOAD - DETECTED TABLES ALREADY FILLED")
      case _ => {
        fillCategories
        fillUserTable
        fillFriendship
        insertConfiguration("DB_SETUP", "CREATE THIS TO PREVENT OTHER INSERTS ON RELOAD")
      }
    }

  }

  def fillUserTable() = {
    insertUser("user1", Crypto.sign("user1"), "User", "1", AuthenticationMethod.UserPassword, "userpass")
    insertUser("diego", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    insertUser("diego.naali@gmail.com", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    insertUser("10203749685571466facebook", Crypto.sign("password"), "Diego", "Fabbro", AuthenticationMethod.OAuth2, "facebook")
    insertOrUpdateOauth2(OAuth2Info(4, "thisTokenWillBeChange", None, Some(5108366), None))


    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        interests += Interest(None, "fantasy", 4, Some(1))
        interests += Interest(None, "horror", 4, Some(1))
        interests += Interest(None, "football", 4, Some(3))

      }
    }
    insertUserInterest(4, 1)
    insertUserInterest(4, 2)
    insertUserInterest(4, 3)
    insertUserInterest(2, 1)
  }

  def fillCategories() {


    insertCategory(Category(None, "book"))
    insertCategory(Category(None, "school"))
    insertCategory(Category(None, "sport"))
  }

  def fillFriendship() {
    //    val friendshipService = new FriendshipService {}
    //    insertFriendship(1,2)
    //    insertFriendship(Friendship(None, 2, 1))
  }
}