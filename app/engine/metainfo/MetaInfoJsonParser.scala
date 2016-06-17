package engine.metainfo

import play.api.libs.functional.syntax._
import play.api.libs.json._

import play.Logger._

case class MetaInfoJsonReport(title: String, sql: Seq[String])

object MetaInfoJsonParser {

  implicit val TemplateReads: Reads[MetaInfoJsonReport] = (
    (__ \ 'title).read[String] and
      (__ \ 'sql).read[List[String]]
    ) (MetaInfoJsonReport.apply _)

  def parse(str: String): MetaInfoJsonReport = {
    val conf = Json.parse(str)
    info(s"conf = $conf")
    (conf).as[MetaInfoJsonReport]
  }

}
