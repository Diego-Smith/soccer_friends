package it.sf.controllers

import play.api.mvc._
import it.sf.models.Category
import scala.util.Random
import it.sf.manager.ComponentRegistry

object CategoryController extends Controller with ComponentRegistry {
  def testAddCategory(name: String) = Action {
    val cat = Category(None, name)
    //	  insertCategory(cat)
    val list = randomInsert
    Ok("ole")
  }

  def listCategories() = Action {
    val categories = categoryRepository.list
    Ok(s"Categories: \n\n${categories.map(_.name).mkString("\n")}")
  }

  def randomInsert() = {
    val numIterations = Random.nextInt(10) + 1
    val range = 0 to numIterations
    val strings = for {
      a <- 0 to numIterations
      number = Random.nextInt(8) + 3
    } yield Random.alphanumeric.slice(0, number).mkString
    strings.foreach(cat => categoryRepository.insertCategory(Category(None, cat)))
    strings
  }

}