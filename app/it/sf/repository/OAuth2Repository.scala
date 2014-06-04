package it.sf.repository

import it.sf.models.OAuth2InfoTable
import scala.Some
import it.sf.models.OAuth2Info
import scala.slick.lifted
import scala.slick.lifted.TableQuery
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.Session
/**
 * Created by diego on 04/06/14.
 */
class OAuth2Repository {
  val oAuth2Info = TableQuery[OAuth2InfoTable]

  def insertOauth2(oauth2: OAuth2Info) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        oAuth2Info += oauth2
      }
    }
  }

  def updateOauth2(oauth2: OAuth2Info) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        val recordToUpdate: lifted.Query[OAuth2InfoTable, OAuth2InfoTable#TableElementType] = oAuth2Info.filter(_.userId === oauth2.userId)
        recordToUpdate.update(oauth2)
      }
    }
  }

  def insertOrUpdateOauth2(oauth2: OAuth2Info) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        val recordToUpdate: lifted.Query[OAuth2InfoTable, OAuth2InfoTable#TableElementType] = oAuth2Info.filter(_.userId === oauth2.userId)
        val firstOption: Option[OAuth2InfoTable#TableElementType] = recordToUpdate.firstOption
        firstOption match {
          case Some(up) =>  recordToUpdate.update(oauth2)
          case _ => insertOauth2(oauth2)
        }
      }
    }
  }
}
