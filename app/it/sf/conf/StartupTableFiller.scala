package it.sf.conf

import it.sf.logger.ApplicationLoggerImpl
import play.api.Play.current
import it.sf.models._
import scala.slick.driver.H2Driver.simple._
import slick.driver.H2Driver.backend.DatabaseDef
import play.api.db.DB
import it.sf.service._
import it.sf.models.Category
import securesocial.core.AuthenticationMethod
import play.api.libs.Crypto
import scala.Some
import it.sf.models.OAuth2Info
import it.sf.models.Category
import it.sf.models.UserInterest.UserInterest

object StartupTableFiller extends ApplicationLoggerImpl with UserService with CategoryService with FriendshipService with OAuth2Service with InterestService
    with UserInterestService {
  def obtainDB: DatabaseDef = Database.forDataSource(DB.getDataSource())

  def startupFill() = {
    obtainDB
    fillCategories
    fillUserTable
    fillFriendship
  }

  def fillUserTable() = {
    insertUser("user1", Crypto.sign("user1"), "User", "1", AuthenticationMethod.UserPassword, "userpass")
    insertUser("diego", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    insertUser("diego.naali@gmail.com", Crypto.sign("diego"), "Diego", "Smith", AuthenticationMethod.UserPassword, "userpass")
    insertUser("10203749685571466facebook", Crypto.sign("password"), "Diego", "Fabbro", AuthenticationMethod.OAuth2, "facebook")
    insertOrUpdateOauth2(OAuth2Info(4, "thisTokenWillBeChanged", None,Some(5108366),None))


    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        interests += Interest(None, "fantasy", 4, Some(1))
        interests += Interest(None, "horror", 4, Some(1))
        interests += Interest(None, "football", 4, Some(3))

        userInterests += UserInterest(None, 4, 1)
        userInterests += UserInterest(None, 4, 2)
        userInterests += UserInterest(None, 4, 3)
        userInterests += UserInterest(None, 2, 3)
      }
    }

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