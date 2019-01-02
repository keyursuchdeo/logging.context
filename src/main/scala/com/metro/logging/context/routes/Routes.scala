package com.metro.logging.context.routes

import java.util.UUID

import akka.actor._
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.metro.logging.context.actors.BasePerRequestActor
import com.metro.logging.context.actors.BasePerRequestActor.{HandleRequest, RequestHandler}
import com.metro.logging.context.utils.{ActorEssentials, AppLogger}
import kamon.akka.http.KamonTraceDirectives
import org.slf4j.{Logger, LoggerFactory}


abstract class Routes extends KamonTraceDirectives {

  protected lazy val logger: AppLogger = AppLogger(this.getClass)

  protected def requestMethod(req: HttpRequest): String = req.method.name

  protected def processRequest(requestHandler: RequestHandler, props: (HttpResponse => Unit) => Props): Route =
    completeWith[HttpResponse](implicitly[ToResponseMarshaller[HttpResponse]]) {
      // TODO Use debugging directive to log request
      completerFunction =>
        perRequestActor(completerFunction, props(completerFunction)) ! HandleRequest(requestHandler)
    }

  protected def perRequestActor(completerFunction: HttpResponse => Unit, props: Props): ActorRef =
    ActorEssentials.actorSystem
      .actorOf(props, s"${BasePerRequestActor.name}-${UUID.randomUUID}")
}

