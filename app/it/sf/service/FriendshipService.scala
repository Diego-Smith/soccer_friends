package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{User, FriendshipTable, Friendship}
import play.api.db.slick.Session
import scala.language.implicitConversions
import it.sf.manager.ComponentRegistry

trait FriendshipService extends ComponentRegistry {
  val friendShips = TableQuery[FriendshipTable]

  def validateFriendshipAndReturnCorrectValue(idUserA: Long, idUserB: Long): (Boolean, String) = (true, "")

  def insertFriendship(idUserA: Long, idUserB: Long): FriendshipInsertResult = validateFriendshipAndReturnCorrectValue(idUserA, idUserB) match {
    case (true, _) =>
      play.api.db.slick.DB.withSession {
        implicit session: Session =>
          val couple = if (idUserA < idUserB) (idUserA, idUserB) else (idUserB, idUserA)
          val result = (friendShips returning friendShips.map(_.id)) += Friendship(None, couple._1, couple._2)
          FriendshipInsertResult(Some(Friendship(Some(result), couple._1, couple._2)), result = true)
      }
    case _ => FriendshipInsertResult(None, result = false)
  }

  implicit def listToUserIdList[Int](input: Seq[scala.Long]) = new userService.UserIdList(input)

  //TODO: compile query?
  def getFriends(userId: Long): Seq[User] = play.api.db.slick.DB.withSession {
    implicit session: Session =>
    (friendShips.filter(_.fkUserA === userId).map(_.fkUserB) ++ friendShips.filter(_.fkUserB === userId).map(_.fkUserA)).run.getUsers
  }
}

case class FriendshipInsertResult(friendship: Option[Friendship], result: Boolean)