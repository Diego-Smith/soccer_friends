package it.sf.service

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.TableQuery
import it.sf.models.CategoryTable
import it.sf.models.Category
import play.api.Play.current
import play.api.db.slick.Session

trait CategoryService {
  val categories = TableQuery[CategoryTable]

  def insertCategory(category: Category) = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        categories += category
    }
  }

  def list: List[Category] = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        categories.list
    }
  }
}