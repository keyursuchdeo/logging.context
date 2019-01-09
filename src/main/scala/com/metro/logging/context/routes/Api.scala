package com.metro.logging.context.routes

import akka.http.scaladsl.server.Directives.{handleExceptions, handleRejections, _}
import akka.http.scaladsl.server._
//import ch.megard.akka.http.cors.CorsDirectives.cors
import com.metro.logging.context.models.CountryKey
import com.metro.logging.context.routes.TestLoggingContextRoutes.traceName

abstract class Api extends ApiHandlers {
  protected val apiRoot: String

  protected def routes(countryKey: CountryKey): Route

  private lazy val wrappedRoutes: Route = {
    extractUnmatchedPath { path =>
      extractMethod { method =>
        pathPrefix(apiRoot) {
          pathPrefix("api" / Segment) { country =>
            val countryKey = CountryKey(country.toUpperCase)
            val traceNameVal = s"${method.value}  $path"
            traceName(traceNameVal, Map("country" -> countryKey.country)) {
              routes(countryKey)
            }
          }
        }

      }
    }
  }

  def getRoutes: Route = {
    val route =
      //cors() {
        handleRejections(validationsRejectionHandler) {
          handleExceptions(exceptionHandler) {
            new HealthCheckRoutes().route ~ wrappedRoutes
          }
        }
      //}
    route
  }

}

