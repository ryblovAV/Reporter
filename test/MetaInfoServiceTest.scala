import models.MetaInfoReport
import org.scalatest.FunSuite
import services.MetaInfoService

class MetaInfoServiceTest extends FunSuite {

  test("build") {
    val path = "test//report-meta-info"
    val metaInfoArray: Map[String, MetaInfoReport] = MetaInfoService.build(path)
    assert(metaInfoArray === Map(
      "report_1" -> MetaInfoReport(id = "report_1", title = "dummyReport", sql = "select * from dual"),
      "report_2" -> MetaInfoReport(id = "report_2",title = "dummyCountReport", sql = "select cunt(*) as cnt from dual"))
    )
  }

}
