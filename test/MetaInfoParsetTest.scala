import engine.metainfo.{MetaInfoJsonParser, MetaInfoJsonReport}
import org.scalatest.FunSuite

class MetaInfoParsetTest extends FunSuite {
  test("parser") {

    val str =
      s"""
         |{
         |  "title": "TestReport",
         |  "sql": [
         |    "select *",
         |    "  from dual"
         |  ]
         |}
       """.stripMargin

    val metaInfo = MetaInfoJsonParser.parse(str)
    assert(metaInfo === MetaInfoJsonReport(title = "TestReport",sql = Seq("select *","  from dual")))
  }
}
