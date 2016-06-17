package controllers

import models.ReportStorage
import play.api.mvc._

class ListReportController extends Controller{

  def listReport = Action {
    Ok(views.html.listReport(ReportStorage.list))
  }

}
