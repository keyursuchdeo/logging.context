package com.metro.logging.context.routes

import akka.http.scaladsl.server.Directives.{get, _}
import akka.http.scaladsl.server.Route
import com.metro.logging.context.actors.PerRequestActor
import com.metro.logging.context.actors.PerRequestActor.TestLoggingContext
import kamon.trace.Tracer

object TestLoggingContextRoutes extends Routes {
  def route(country: String): Route =
    pathPrefix("test") {
      testLoggingContext(country)
    }

  private def testLoggingContext(country: String): Route =
    get {
      logger.info("Received request to test logging context")
      processRequest(TestLoggingContext, PerRequestActor.props)
    }
}