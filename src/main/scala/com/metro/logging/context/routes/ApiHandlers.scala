package com.metro.logging.context.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes.NotFound
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._

trait ApiHandlers {
  val exceptionHandler = ExceptionHandler {
    case ex: IllegalArgumentException =>
      complete(StatusCodes.PreconditionFailed, s"Input validation failed: ${ex.getMessage}")
    case ex: Exception =>
      complete(StatusCodes.InternalServerError, s"Unexpected error occurred: ${ex.getMessage}")
  }

  def validationsRejectionHandler: RejectionHandler =
    RejectionHandler.newBuilder
      .handle {
        case ValidationRejection(msg, _) =>
          complete(StatusCodes.PreconditionFailed, s"Input validation failed: $msg")
        case MalformedQueryParamRejection(field, error, None) =>
          complete(StatusCodes.BadRequest, s"Invalid input received $field $error")
        case MissingQueryParamRejection(field) =>
          complete(StatusCodes.BadRequest, s"Missing input field - $field")
      }
      .handleNotFound {
        complete((NotFound, "Not here!"))
      }
      .result()

}

