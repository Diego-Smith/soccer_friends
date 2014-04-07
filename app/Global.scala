import play.api.db.DB
import play.api.GlobalSettings
import scala.slick.driver.H2Driver.simple._
import play.api.Application
import play.api.Play.current
import service.UserService2
import models.User
import conf.StartupTableFiller

object Global extends GlobalSettings with StartupTableFiller {
  override def onStart(app: Application) {
    println("hello ---- PLAY");
    lazy val database = Database.forDataSource(DB.getDataSource())
    //    insertUser(User(None,"test","test"))
    fillUserTable
  }

}