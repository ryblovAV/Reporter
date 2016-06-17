package models

import engine.metainfo.MetaInfoJsonReport

case class MetaInfoReport(id: String, title: String, sql: String)

object MetaInfoReport {
  def create(id: String, metaJson: MetaInfoJsonReport):MetaInfoReport = {
    MetaInfoReport(id = id, title = metaJson.title, sql = metaJson.sql.mkString(" "))
  }
}


