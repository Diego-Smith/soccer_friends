package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models.{User, FriendshipTable, Friendship}
import play.api.db.slick.Session

trait FriendshipService extends UserService {
  val friendShips = TableQuery[FriendshipTable]

  def validateFriendshipAndReturnCorrectValue(idUserA: Int, idUserB: Int): (Boolean, String) = (false, "")
//
//  def friendshiplist: List[Friendship] = {
//    play.api.db.slick.DB.withSession {
//      implicit session: Session =>
//        friendShips.list
//    }
//  }

  def insertFriendship(idUserA: Int, idUserB: Int): FriendshipInsertResult = validateFriendshipAndReturnCorrectValue(idUserA, idUserB) match {
    case (true, _) =>
      play.api.db.slick.DB.withSession {
        implicit session: Session =>
          val couple = if (idUserA < idUserB) (idUserA, idUserB) else (idUserB, idUserA)
          val result = (friendShips returning friendShips.map(_.id)) += Friendship(None, couple._1, couple._2)
          FriendshipInsertResult(Some(Friendship(Some(result), couple._1, couple._2)), result = true)
      }
    case _ => FriendshipInsertResult(None, result = false)
  }

  //TODO: compile query?
  def getFriends(userId: Int) = play.api.db.slick.DB.withSession {
    implicit session: Session =>
      val q1 = friendShips.filter(_.fkUserA === userId).map(_.fkUserB)
      val q2 = friendShips.filter(_.fkUserB === userId).map(_.fkUserA)
      val idUsers: Seq[Int] = (q1 ++ q2).run
      val users: Seq[User] = idUsers.map(findUserById(_)).flatten
      users
  }

}

case class FriendshipInsertResult(friendship: Option[Friendship], result: Boolean)