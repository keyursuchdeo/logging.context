package com.metro.logging.context.routes

import akka.http.scaladsl.server.Route
import com.metro.logging.context.models.CountryKey

object LoggingContextApi extends Api {

  override val apiRoot: String = "loggingcontext"

  override protected def routes(countryKey: CountryKey): Route =
    TestLoggingContextRoutes.route(countryKey.country)

}

