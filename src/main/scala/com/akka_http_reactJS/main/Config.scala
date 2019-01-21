package com.akka_http_reactJS.main

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import slick.driver.PostgresDriver.api._

object Config {

  private lazy val config = com.typesafe.config.ConfigFactory.load()
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val Address = config.getString("address")
  val Port = config.getInt("port")

  val db = Database.forConfig("postgres")

  implicit val session: Session = db.createSession()

}
