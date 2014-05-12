package it.sf.models

import scala.slick.lifted.ProvenShape.proveShapeOf
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.Config.driver.simple.Tag
import it.sf.service.UserService

/**
 * Created by diego on 12/05/14.
 */
case class OAuth2Info(userId: Int, accessToken: String, tokenType: Option[String], expiresIn: Option[Int], refreshToken: Option[String])

class OAuth2InfoTable(tag: Tag) extends Table[OAuth2Info](tag, "OAUTH2INFO") with UserService {
  def userId = column[Int]("USER_ID", O.PrimaryKey)

  def accessToken = column[String]("ACCESS_TOKEN", O.NotNull)
  def tokenType = column[String]("TOKEN_TYPE", O.Nullable)
  def expiresIn = column[Int]("EXIPIRES_IN", O.Nullable)
  def refreshToken = column[String]("REFRESH_TOKEN", O.Nullable)

  def * = (userId, accessToken, tokenType.?, expiresIn.?, refreshToken.?) <>(OAuth2Info.tupled, OAuth2Info.unapply)
  def userA = foreignKey("FK_USER_ID", userId, users)(_.id)
}


//(val accessToken : scala.Predef.String, val tokenType : scala.Option[scala.Predef.String] =
// { /* compiled code */ }, val expiresIn : scala.Option[scala.Int] = { /* compiled code */ },
// val refreshToken : scala.Option[scala.Predef.String] = { /* compiled code */ }) extends scala.AnyRef with scala.Product with scala.Serializable {