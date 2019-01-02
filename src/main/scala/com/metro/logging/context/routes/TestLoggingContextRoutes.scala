package com.metro.logging.context.routes

import akka.http.scaladsl.server.Directives.{get, _}
import akka.http.scaladsl.server.Route
import com.metro.logging.context.actors.PerRequestActor
import com.metro.logging.context.actors.PerRequestActor.TestLoggingContext
import kamon.trace.Tracer
import org.slf4j.MDC

object TestLoggingContextRoutes extends Routes {
  def route(country: String): Route =
    pathPrefix("test") {
      testLoggingContext(country)
    }

  private def testLoggingContext(country: String): Route =
    get {
      traceName("test-logging-context") {
        Tracer.currentContext.addTag("country", country)
        logger.info("Received request to test logging context")
        processRequest(TestLoggingContext, PerRequestActor.props)
      }
    }
}