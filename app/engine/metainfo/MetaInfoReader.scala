package engine.metainfo

import java.io.File

import scala.io.Source

object MetaInfoReader {

  def read(f: File) = {
    val bufferedSource = Source.fromFile(f,"UTF8")
    val jsonStr = bufferedSource.getLines.mkString("")
    bufferedSource.close
    jsonStr
  }

}
