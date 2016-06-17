package models

import services.MetaInfoService

object ReportStorage {

  private def load = MetaInfoService.build("report-meta-info")

  def list(): Seq[MetaInfoReport] = load.values.toSeq

  def get(id: String): Option[MetaInfoReport] = load.get(id)


}
