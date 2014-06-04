import it.sf.logger.LoggerManager
import play.api.GlobalSettings
import play.api.Application
import it.sf.conf.StartupTableFiller


object Global extends GlobalSettings with LoggerManager {
  override def onStart(app: Application) {
    logger.info("Application Started")
    // lazy val database = Database.forDataSource(DB.getDataSource())

    StartupTableFiller.startupFill

  }

}
