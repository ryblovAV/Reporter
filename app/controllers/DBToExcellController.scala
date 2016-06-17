package controllers

import play.Logger._
import play.api.mvc._
import services.DbToExcelService

import scala.util.{Failure, Success}

class DBToExcellController extends Controller  {



  def export(id: String) = Action {
    request =>

      info(s"start: report:$id; id:${request.id}; address:${request.remoteAddress}")

      DbToExcelService.export(id, request.id) match {
        case Success(file) => Ok.sendFile(file)
        case Failure(e) => Ok(views.html.index(e.toString))
      }

  }

}
