package com.akka_http_reactJS.db.changelog

import java.sql.Connection

import akka.actor.ActorSystem
import com.akka_http_reactJS.db.IOUtils
import com.akka_http_reactJS.main.Config.db
import com.typesafe.config.ConfigFactory
import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.{ ClassLoaderResourceAccessor, CompositeResourceAccessor, FileSystemResourceAccessor }

object LiquibasePlugin {

  lazy val config = ConfigFactory.load()

  def performMigrations: Unit = {

    val changeLog = config.getString("liquibase.changelog")

    IOUtils.ensureClose(db.source.createConnection()) { connection =>
      val liqui = try {
        getLiquibase(changeLog, connection)
      } catch {
        case e: Exception => throw new Exception("Liquibase can't be instantiated")
      }
      liqui.update("dev")
    }

  }

  def getLiquibase(changeLogFilePath: String, connection: Connection): Liquibase = {
    val fileAccessor = new FileSystemResourceAccessor()
    val classLoaderAccessor = new ClassLoaderResourceAccessor(classLoader)
    val fileOpener = new CompositeResourceAccessor(fileAccessor, classLoaderAccessor)
    new Liquibase(changeLogFilePath, fileOpener, new JdbcConnection(connection))
  }

  def classLoader: ClassLoader = classOf[ActorSystem].getClassLoader
}
