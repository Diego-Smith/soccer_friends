package service

import scala.slick.lifted.TableQuery
import models.Categories

trait CategoryService {
  val categories = TableQuery[Categories]
}