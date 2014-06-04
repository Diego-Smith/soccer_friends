package it.sf.models

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.ProvenShape
import com.github.tototoshi.slick.H2JodaSupport._
import org.joda.time.DateTime

/**
 * Created by diego on 12/05/14.
 */
case class UserPendentRequest(email: String, token: String, creationTime: DateTime, expirationTime: DateTime, isSignup: Boolean)

class UserPendentRequestTable(tag: Tag) extends Table[UserPendentRequest](tag, "USER_PENDENT_REQUEST") {
  val email = column[String]("EMAIL", O.NotNull)
  val token = column[String]("TOKEN", O.NotNull)
  val creationTime = column[DateTime]("CREATION_TIME", O.NotNull)
  val expirationTime = column[DateTime]("EXPIRATION_TIME", O.NotNull)
  val isSignUp = column[Boolean]("IS_SIGNUP", O.NotNull)
  override def * : ProvenShape[UserPendentRequest] = (email, token, creationTime, expirationTime, isSignUp) <> (UserPendentRequest.tupled,UserPendentRequest.unapply)

  def tokenUnique = index("UNIQUE_TOKEN", token, unique = true)
}

//case class Token(val uuid : scala.Predef.String, val email : scala.Predef.String, val creationTime : org.joda.time.DateTime,
// val expirationTime : org.joda.time.DateTime, val isSignUp : scala.Boolean) extends scala.AnyRef with scala.Product with scala.Serializable {

