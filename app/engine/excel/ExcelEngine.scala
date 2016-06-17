package engine.excel

import java.io.File
import java.util.Date

import engine.db.DBEngine._
import engine.db.{DBData, MetaField}
import models.MetaInfoReport
import org.apache.poi.xssf.usermodel.{XSSFCell, XSSFRow, XSSFWorkbook}

import scala.util.Try

object CellMapper {

  def strToCell(v: Any, cell: XSSFCell) = if (v != null) cell.setCellValue(v.asInstanceOf[String])
  def doubleToCell(v: Any, cell: XSSFCell) = if (v != null) cell.setCellValue(v.asInstanceOf[Double])
  def dateToCell(v: Any, cell: XSSFCell) = if (v != null) cell.setCellValue(v.asInstanceOf[Date])
  def unknownTypeToCell(v: Any, cell: XSSFCell) = cell.setCellValue("?")

  def mapper(meta:MetaField):((Any,XSSFCell) => Unit) = meta match {
    case MetaField(_,STRING_TYPE) => strToCell
    case MetaField(_,CHAR_TYPE) => strToCell
    case MetaField(_,DOUBLE_TYPE) => doubleToCell
    case MetaField(_,DATE_TYPE) => dateToCell
    case _ => unknownTypeToCell
  }

}

object ExcelEngine {

  val path = "reports"

  def createTitle(titles: Seq[String], row: XSSFRow) = {
    for ((title,i) <- titles.zipWithIndex) {
      row.createCell(i).setCellValue(title)
    }
  }

  def buildExcelRow(row: XSSFRow,
                    cellsValue: IndexedSeq[Any],
                    mappers: IndexedSeq[(Any,XSSFCell) => Unit]) = {
    for (i <- 0 until cellsValue.length) {
      mappers(i)(cellsValue(i), row.createCell(i))
    }
  }

  def addSql(workbook: XSSFWorkbook, sql: String) = {
    workbook.createSheet("SQL").createRow(0).createCell(0).setCellValue(sql)
  }

  def export(meta: MetaInfoReport, dbData: DBData) = {

    val workbook = new XSSFWorkbook
    val sheet = workbook.createSheet(meta.title)

    createTitle(
      titles = dbData.metaFields.map(_.name),
      row = sheet.createRow(0)
    )

    var i = 1
    for (row <- dbData.data) {
      buildExcelRow(
        row = sheet.createRow(i),
        cellsValue = row,
        mappers = dbData.metaFields.map(CellMapper.mapper(_)))
      i += 1
    }

    addSql(workbook, meta.sql)

    workbook
  }

  def createExcelFile(meta: MetaInfoReport, dbData: DBData): Try[File] = {


    import java.io.{FileOutputStream, File => JFile}

    Try {

      val path = "reports"

      (new File(path)).mkdirs()

      val file = new JFile(s"$path//${meta.title}.xlsx")
      val fileOut = new FileOutputStream(file)

      val wb = export(meta, dbData)
      wb.write(fileOut)
      fileOut.close()

      file
    }

  }



}
