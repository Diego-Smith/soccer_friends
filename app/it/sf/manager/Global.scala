import play.api.db.DB
import play.api.GlobalSettings
import scala.slick.driver.H2Driver.simple._
import play.api.Application
import play.api.Play.current
import it.sf.conf.StartupTableFiller

object Global extends GlobalSettings with StartupTableFiller {
  override def onStart(app: Application) {
    println("hello ---- PLAY");
    lazy val database = Database.forDataSource(DB.getDataSource())
    //    insertUser(User(None,"test","test"))
    fillUserTable
  }
}