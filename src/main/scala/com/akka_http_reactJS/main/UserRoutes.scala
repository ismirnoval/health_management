package com.akka_http_reactJS.main

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{get, post}
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.util.Timeout
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import com.akka_http_reactJS.db.entities.{DrugReserve, DrugReserveServiceImpl}

import scala.concurrent.duration._
import Config._
import akka.http.scaladsl.model.StatusCodes

import scala.util.parsing.json.{JSON, JSONArray}

//#user-routes-class
trait UserRoutes {
  //#user-routes-class

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[UserRoutes])

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  //#all-routes
  //#users-get-post
  //#users-get-delete   
  lazy val userRoutes: Route =
    pathPrefix("drugs") {
      concat(
        //#users-get-delete
        pathEnd {
          concat(
            get {
              val thingsF = {
                val lostThingsF = DrugReserveServiceImpl.list
                for {
                  lostThings <- lostThingsF
                } yield (lostThings)
              }
              onSuccess(thingsF) { case (lost) =>
                complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, JSON(lost)))
              }
            }
          )
        },
        //#users-get-post
        //#users-get-delete
        path(Segment) { name =>
          concat(
            get {
              complete("hello")
            }
          )
        }
      )
      //#users-get-delete
    }
  //#all-routes
}
