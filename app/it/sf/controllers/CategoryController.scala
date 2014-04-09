package it.sf.controllers

import play.api.mvc.Controller
import it.sf.service.CategoryService
import play.api._
import play.api.mvc._
import it.sf.models.Category
import scala.util.Random

object CategoryController extends Controller with CategoryService {
	def testAddCategory(name: String) = Action {
	  val cat = Category(None,name)
//	  insertCategory(cat)
	  val list = randomInsert
	  Ok("ole")
	}
	
	def listCategories() = Action {
	  val categories = list
	  Ok(s"Categorie: \n\n${categories.map(_.name).mkString("\n")}")
	}
	
	def randomInsert() = {
	  val numIterations = Random.nextInt(10) + 1
	  val range = (0 to numIterations)
	  val strings = for {
	    a <- 0 to numIterations
	    number = Random.nextInt(8) + 3
	  } yield Random.alphanumeric.slice(0, number).mkString
	  strings.foreach(cat => insertCategory(Category(None, cat)))
	  strings
	}
	
}