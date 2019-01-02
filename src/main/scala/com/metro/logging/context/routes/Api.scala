package com.metro.logging.context.routes

import akka.http.scaladsl.server.Directives.{handleExceptions, handleRejections, _}
import akka.http.scaladsl.server._
import ch.megard.akka.http.cors.CorsDirectives.cors
import com.metro.logging.context.models.CountryKey

abstract class Api extends ApiHandlers {
  protected val apiRoot: String

  protected def routes(countryKey: CountryKey): Route

  private lazy val wrappedRoutes: Route = {
    pathPrefix(apiRoot) {
      pathPrefix("api" / Segment) { country =>
        val countryKey = CountryKey(country.toUpperCase)
        routes(countryKey)
      }
    }
  }

  def getRoutes: Route = {
    val route =
      cors() {
        handleRejections(validationsRejectionHandler) {
          handleExceptions(exceptionHandler) {
            new HealthCheckRoutes().route ~ wrappedRoutes
          }
        }
      }
    route
  }

}

