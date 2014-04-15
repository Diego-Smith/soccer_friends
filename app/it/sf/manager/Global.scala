import play.api.GlobalSettings
import play.api.Application
import it.sf.conf.StartupTableFiller

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    println("hello ---- PLAY");
    //    lazy val database = Database.forDataSource(DB.getDataSource())

    StartupTableFiller.fillUserTable
    StartupTableFiller.fillCategories

  }
}