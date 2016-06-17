package services

import java.io.File

import engine.metainfo.{MetaInfoJsonParser, MetaInfoReader}
import models.MetaInfoReport

object MetaInfoService {

  def build(path: String): Map[String, MetaInfoReport] = {
    new File(path).listFiles()
      .filter(f => !f.isDirectory)
      .filter(f => f.getName.takeRight(5) == ".json")
      .map(
        f => {
          val id = f.getName.dropRight(5)
          (id, MetaInfoReport.create(
            id = id,
            metaJson = MetaInfoJsonParser.parse(MetaInfoReader.read(f)))
          )
        }
      ).toMap
  }

}
