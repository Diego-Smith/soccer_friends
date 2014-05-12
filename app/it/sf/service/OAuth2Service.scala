package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{OAuth2Info, OAuth2InfoTable, PageVisitedTable, PageVisited}
import play.api.db.slick.Session


trait OAuth2Service {
  val oAuth2Info = TableQuery[OAuth2InfoTable]

  def insertOauth2(oauth2: OAuth2Info) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session => {
        oAuth2Info += oauth2
      }
    }
  }
}