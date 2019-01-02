package com.metro.logging.context.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path, pathPrefix, _}
import akka.http.scaladsl.server.Route

class HealthCheckRoutes extends Routes {
  val route: Route =
    pathPrefix(".well-known") {
      path("live") {
        (get | head) {
          complete(StatusCodes.NoContent)
        }
      } ~ path("ready") {
        (get | head) {
          complete(StatusCodes.NoContent)
        }
      }
    }
}

