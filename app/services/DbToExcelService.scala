package services

import engine.db.DBEngine
import engine.excel.ExcelEngine
import models.ReportStorage
import play.Logger._

import scala.util.Failure

object DbToExcelService {

  def export(reportId: String, requestId: Long) = {

    val optResult = ReportStorage.get(reportId) match {
      case Some(meta) =>
        for {
          dbData <- DBEngine.readFromDB(meta.sql)
          file <- ExcelEngine.createExcelFile(meta,dbData)
        } yield file
      case None => Failure(new Exception(s"Not found report: $reportId"))
    }

    info(s"complete: report=$reportId; id=$requestId")

    optResult

  }
}
