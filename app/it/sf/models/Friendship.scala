package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import it.sf.manager.ComponentRegistry

/**
 * Created by diego on 02/05/14.
 */
case class Friendship(id: Option[Long]= None, idUserA: Long, idUserB: Long)

class FriendshipTable(tag: Tag) extends Table[Friendship](tag, "FRIENDSHIP") with ComponentRegistry {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def fkUserA = column[Long]("ID_USER_A", O.NotNull)
  def fkUserB = column[Long]("ID_USER_B", O.NotNull)

  def * = (id.?, fkUserA, fkUserB) <>(Friendship.tupled, Friendship.unapply)

  def userA = foreignKey("FK_USER_A", fkUserA, userRepository.users)(_.id)
  def userB = foreignKey("FK_USER_B", fkUserB, userRepository.users)(_.id)

  def uniqueFriendshipIndex = index("uniqueFriendshipIndex", (fkUserA,fkUserB), unique = true)
}
