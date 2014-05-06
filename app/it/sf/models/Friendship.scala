package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.service.UserService

/**
 * Created by diego on 02/05/14.
 */
case class Friendship(id: Option[Int]= None, idUserA: Int, idUserB: Int)

class FriendshipTable(tag: Tag) extends Table[Friendship](tag, "FRIENDSHIP") with UserService {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def fkUserA = column[Int]("ID_USER_A")
  def fkUserB = column[Int]("ID_USER_B")

  def * = (id.?, fkUserA, fkUserB) <>(Friendship.tupled, Friendship.unapply)

  def userA = foreignKey("FK_USER_A", fkUserA, users)(_.id)
  def userB = foreignKey("FK_USER_B", fkUserB, users)(_.id)

  def uniqueFriendshipIndex = index("uniqueFriendshipIndex", (fkUserA,fkUserB), true)
}
