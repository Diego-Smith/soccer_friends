package it.sf.service

import play.api.db.slick.Config.driver.simple._
import play.api.Play.current
import it.sf.models._
import play.api.db.slick.{Session, DB}
import scala.slick.lifted
import org.joda.time.{DateTime, DateTimeZone}
import com.github.tototoshi.slick.H2JodaSupport._

trait UserPendentRequestService {
  val requests = TableQuery[UserPendentRequestTable]

  def insertPendentRequest(userPendentRequest: UserPendentRequest) : Boolean = {
    DB.withSession {
      implicit session: Session => {
        requests += userPendentRequest
        true
      }
    }
  }

  def findPendentRequest(token: String): Option[UserPendentRequest] = {
    DB.withSession {
      implicit session: Session => {
        requests.filter(_.token === token).firstOption
      }
    }
  }

  def deletePendentRequest(token: String) = {
    DB.withSession {
      implicit session: Session => {
        val selectRecord = requests.filter(_.token === token).first
        val deleteRecords: lifted.Query[UserPendentRequestTable, UserPendentRequestTable#TableElementType] = requests.filter(_.email === selectRecord.email)
        deleteRecords.delete
      }
    }
  }

  def deleteOldPendentRequests() = {
    DB.withSession {
      implicit session: Session => {
        val now = DateTime.now

        val q1 = for {
          jt <- requests
          if jt.expirationTime < now
        } yield jt

        q1.delete
      }
    }
  }
}