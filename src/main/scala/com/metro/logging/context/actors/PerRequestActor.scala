package com.metro.logging.context.actors

import akka.actor.{ActorRef, Props, Terminated}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.metro.logging.context.actors.BasePerRequestActor.RequestHandler
import com.metro.logging.context.actors.BusinessActor.ProcessBusinessRequest
import com.metro.logging.context.actors.PerRequestActor.{Done, TestLoggingContext}
import com.metro.logging.context.utils.AppLogger
import kamon.trace.Tracer
import org.slf4j.{Logger, LoggerFactory, MDC}

object PerRequestActor {
  case object TestLoggingContext extends RequestHandler
  case object Done

  def props(completerFunction: HttpResponse => Unit): Props =
    Props(new PerRequestActor(completerFunction))
}

class PerRequestActor(completerFunction: HttpResponse => Unit) extends BasePerRequestActor(completerFunction) {

  protected val logger: AppLogger = AppLogger(this.getClass)

  override protected def handleRequest: Receive = {
    case TestLoggingContext =>
      logger.info("Received req to test logging context in PerRequestActor")
      val actor = createAndWatchActor(BusinessActor.props, completerFunction, BusinessActor.name)
      actor ! ProcessBusinessRequest
    case Done =>
      done()
    case Terminated(terminatedActorRef) =>
      handleTerminatedChildActor(terminatedActorRef)
  }

  private def handleTerminatedChildActor(actorRef: ActorRef): Unit = {
    //complete with 500 if termination request if not from Initial load actor
    self ! Done

  }
}
