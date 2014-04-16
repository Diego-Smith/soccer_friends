import it.sf.logger.ApplicationLoggerImpl
import play.api.GlobalSettings
import play.api.Application
import it.sf.conf.StartupTableFiller

object Global extends GlobalSettings with ApplicationLoggerImpl {
  override def onStart(app: Application) {
    logConsole("Application Started")
    //    lazy val database = Database.forDataSource(DB.getDataSource())

    StartupTableFiller.fillUserTable
    StartupTableFiller.fillCategories

  }
}