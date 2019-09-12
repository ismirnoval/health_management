package com.drug.db.entities

import java.sql.Timestamp

import com.drug.db.CustomPostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

case class UsedDrugs(
  id: String,
  dateInjection: Timestamp,
  drugId: String,
  causeId: String,
  description: Option[String]
)

trait UsedDrugsService {
  def save(
    id: String,
    dateInjection: Timestamp,
    drugId: String,
    causeId: String,
    description: Option[String]
  ): Future[UsedDrugs]

  def listBy(id: String): Future[Seq[UsedDrugs]]

}

object UsedDrugsServiceImpl extends UsedDrugsService {
  import UsedDrugsTableComponent._
  import com.drug.main.Config.{ db, executionContext }

  override def save(
    id: String,
    dateInjection: Timestamp,
    drugId: String,
    causeId: String,
    description: Option[String]
  ): Future[UsedDrugs] = {
    val usedDrug = UsedDrugs(id, dateInjection, drugId, causeId, description)
    db.run(usedDrugs += usedDrug).map(_ => usedDrug)
  }

  override def listBy(id: String): Future[Seq[UsedDrugs]] = db.run(usedDrugs.filter(_.id === id).result)

}

object UsedDrugsTableComponent {
  class UsedDrugsTable(tag: Tag) extends Table[UsedDrugs](tag, "UsedDrugs") {
    def id = column[String]("id", O.PrimaryKey)
    def dateInjection = column[Timestamp]("dateInjection")
    def drugId = column[String]("drugId")
    def causeId = column[String]("causeId")
    def description = column[Option[String]]("description")

    def * = (id, dateInjection, drugId, causeId, description) <> (UsedDrugs.tupled, UsedDrugs.unapply)
  }

  val usedDrugs = TableQuery[UsedDrugsTable]
}