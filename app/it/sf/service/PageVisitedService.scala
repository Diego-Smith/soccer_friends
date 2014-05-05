package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{PageVisitedTable, PageVisited}
import play.api.db.slick.Session

trait PageVisitedService {
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