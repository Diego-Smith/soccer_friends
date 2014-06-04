package it.sf.repository

import it.sf.models.PageVisitedTable
import it.sf.models.PageVisited
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import play.api.db.slick.Session
/**
 * Created by diego on 04/06/14.
 */
class PageVisitedRepository {
  val pagesVisited = TableQuery[PageVisitedTable]

  def validatePageVisited(visited: PageVisited): Boolean = true

  def insertPageVisited(pageVisited: PageVisited): Integer = validatePageVisited(pageVisited) match {
    case true =>
      play.api.db.slick.DB.withSession {
        implicit session: Session =>
          pagesVisited += pageVisited
      }
    case _ => 0
  }
}
