import it.sf.logger.ApplicationLoggerImpl
import play.api.GlobalSettings
import play.api.Application
import it.sf.conf.StartupTableFiller
import play.api.mvc.{AnyContent, Action}
import play.mvc.Results.Redirect
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object Global extends GlobalSettings with ApplicationLoggerImpl {
  override def onStart(app: Application) {
    logConsole("Application Started")
    //    lazy val database = Database.forDataSource(DB.getDataSource())

    StartupTableFiller.startupFill

  }

}