package com.akka_http_reactJS.db.entities

import java.sql.Timestamp

import com.akka_http_reactJS.db.CustomPostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

case class DrugReserve(
  id: String,
  name: String,
  receiptDate: Timestamp,
  expiryDate: Timestamp,
  dosage: Int,
  amount: Int
)

trait DrugReserveService {
  def save(
    id: String,
    name: String,
    receiptDate: Timestamp,
    expiryDate: Timestamp,
    dosage: Int,
    amount: Int
  ): Future[DrugReserve]

  def listBy(id: String): Future[Seq[DrugReserve]]

  def list: Future[Seq[DrugReserve]]

}

object DrugReserveServiceImpl extends DrugReserveService {
  import DrugReserveTableComponent._
  import com.akka_http_reactJS.main.Config.{ db, executionContext }

  override def save(
    id: String,
    name: String,
    receiptDate: Timestamp,
    expiryDate: Timestamp,
    dosage: Int,
    amount: Int
  ): Future[DrugReserve] = {
    val drugReserve = DrugReserve(id, name, receiptDate, expiryDate, dosage, amount)
    db.run(drugReserves += drugReserve).map(_ => drugReserve)
  }

  override def listBy(id: String): Future[Seq[DrugReserve]] = db.run(drugReserves.filter(_.id === id).result)

  override def list: Future[Seq[DrugReserve]] = db.run(drugReserves.result)

}

object DrugReserveTableComponent {
  class DrugReserveTable(tag: Tag) extends Table[DrugReserve](tag, "DrugReserve") {
    def id = column[String]("id", O.PrimaryKey)
    def name = column[String]("name")
    def receiptDate = column[Timestamp]("receiptDate")
    def expiryDate = column[Timestamp]("expiryDate")
    def dosage = column[Int]("dosage")
    def amount = column[Int]("amount")

    def * = (id, name, receiptDate, expiryDate, dosage, amount) <> (DrugReserve.tupled, DrugReserve.unapply)
  }

  val drugReserves = TableQuery[DrugReserveTable]
}