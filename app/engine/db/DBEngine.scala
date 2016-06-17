package engine.db

import java.sql.{DriverManager, ResultSet}

import conf.DBConfig

import scala.annotation.tailrec
import scala.util.Try

case class MetaField(name: String, fieldType: String)

case class DBData(metaFields: IndexedSeq[MetaField], data: List[IndexedSeq[Any]])

object DBEngine {

  final val CHAR_TYPE = "CHAR"
  final val STRING_TYPE = "VARCHAR2"
  final val DOUBLE_TYPE = "NUMBER"
  final val DATE_TYPE = "DATE"

  def getMetaInfo(rs: ResultSet):IndexedSeq[MetaField] = {
    val m = rs.getMetaData

    val count = m.getColumnCount
    (1 to count).map(
      i => MetaField(name = m.getColumnName(i), m.getColumnTypeName(i))
    )
  }

  def fillRow(rs: ResultSet, metaFields: IndexedSeq[(MetaField,Int)]): IndexedSeq[Any] = {
    metaFields.map {
      case (MetaField(_, STRING_TYPE), index) => rs.getString(index + 1)
      case (MetaField(name, CHAR_TYPE), index) => rs.getString(index + 1)
      case (MetaField(_, DOUBLE_TYPE), index) => rs.getDouble(index + 1)
      case (MetaField(name, DATE_TYPE), index) => rs.getDate(index + 1)
      case (MetaField(name, fieldType), _) => s"Unknown Type $fieldType for $name"
    }
  }

  @tailrec
  def fillData(rs: ResultSet, metaFields: IndexedSeq[(MetaField,Int)], l: List[IndexedSeq[Any]]):List[IndexedSeq[Any]] = {
    if (rs.next()) fillData(rs = rs, metaFields = metaFields, l = fillRow(rs = rs, metaFields = metaFields)::l)
    else l
  }

  def readFromDB(sql: String): Try[DBData] = {

    Class.forName(DBConfig.driver)

    val conn = DriverManager.getConnection(DBConfig.url, DBConfig.username, DBConfig.password)
    Try(
      try {
        val st = conn.createStatement()

        try {
          val rs = st.executeQuery(sql)

          val metaFields = getMetaInfo(rs)
          val data = fillData(rs = rs, metaFields = metaFields.zipWithIndex, l = List.empty)

          DBData(metaFields,data)

        } finally {
          st.close()
        }
      } finally {
        conn.close()
      }
    )
  }

}