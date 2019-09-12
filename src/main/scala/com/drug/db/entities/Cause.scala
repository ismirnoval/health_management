package com.drug.db.entities

import com.drug.db.CustomPostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

case class Cause(id: String, name: String, description: String)

trait CauseService {

  def save(id: String, name: String, description: String): Future[Cause]

  def listBy(id: String): Future[Seq[Cause]]

}

object CauseServiceImpl extends CauseService {
  import CauseTableComponent._
  import com.drug.main.Config.{ db, executionContext }

  override def save(id: String, name: String, description: String): Future[Cause] = {
    val cause = Cause(id, name, description)
    db.run(causes += cause).map(_ => cause)
  }

  override def listBy(id: String): Future[Seq[Cause]] = db.run(causes.filter(_.id === id).result)

}

object CauseTableComponent {
  class CauseTable(tag: Tag) extends Table[Cause](tag, "Cause") {
    def id = column[String]("id", O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")

    def * = (id, name, description) <> (Cause.tupled, Cause.unapply)
  }

  val causes = TableQuery[CauseTable]
}