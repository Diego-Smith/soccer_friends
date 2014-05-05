package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{Friendship, FriendshipTable}
import play.api.db.slick.Session

trait FriendshipService {
  val friendShips = TableQuery[FriendshipTable]

  def validateFriendshipAndReturnCorrectValue(friendship: Friendship): (Boolean, Friendship) = {
    if (friendship.idUserA > friendship.idUserB) {
      (true, Friendship(friendship.id, friendship.idUserB, friendship.idUserA))
    } else {
      (true, friendship)
    }
  }

  def friendshiplist: List[Friendship] = {
    play.api.db.slick.DB.withSession {
      implicit session: Session =>
        friendShips.list
    }
  }

  def insertFriendship(friendship: Friendship): FriendshipInsertResult = validateFriendshipAndReturnCorrectValue(friendship) match {
    case (true, correctValue) =>
      play.api.db.slick.DB.withSession {
        implicit session: Session =>
          val result = (friendShips returning friendShips.map(_.id)) += correctValue
          FriendshipInsertResult(Some(Friendship(Some(result),correctValue.idUserA, correctValue.idUserB)), result = true)
      }
    case _ => FriendshipInsertResult(None, result = false)
  }
}

case class FriendshipInsertResult(friendship: Option[Friendship], result: Boolean)