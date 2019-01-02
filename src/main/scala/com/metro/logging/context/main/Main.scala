package com.metro.logging.context.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.metro.logging.context.routes.LoggingContextApi
import com.metro.logging.context.utils.ActorEssentials
import kamon.Kamon
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  Kamon.start()

  implicit val actorSystem: ActorSystem        = ActorEssentials.actorSystem
  implicit val materializer: ActorMaterializer = ActorEssentials.materializer
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor =
    ActorEssentials.actorSystem.dispatcher

  val routes: Route = LoggingContextApi.getRoutes
  val logger = LoggerFactory.getLogger(this.getClass)

  val host          = "localhost"
  val port          = 8080
  Http().bindAndHandle(routes, host, port)
  logger.info(s"Server online at http://$host:$port")

}
