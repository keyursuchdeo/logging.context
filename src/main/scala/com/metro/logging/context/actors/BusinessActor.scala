package com.metro.logging.context.actors

import akka.actor.{ActorRef, Props}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.metro.logging.context.actors.BusinessActor.ProcessBusinessRequest
import com.metro.logging.context.actors.DataAccessActor.{ProcessDARequest, RequestProcessed}
import com.metro.logging.context.actors.PerRequestActor.Done
import com.metro.logging.context.utils.AppLogger

object BusinessActor {
  def props(completerFunction: HttpResponse => Unit): Props =
    Props(new BusinessActor(completerFunction))
  lazy val name: String = this.getClass.getSimpleName

  case object ProcessBusinessRequest
}

class BusinessActor(completerFunction: HttpResponse => Unit) extends BaseActor {
  private val daActor: ActorRef = createActor(DataAccessActor.props, DataAccessActor.name)
  private val logger: AppLogger = AppLogger(this.getClass)
  override def receive: Receive = {
    case ProcessBusinessRequest =>
      logger.info("Received req to test logging context in BusinessActor")
      daActor ! ProcessDARequest
    case RequestProcessed =>
      logger.info("Req to test logging context processed in BusinessActor")
      completerFunction(HttpResponse(status = StatusCodes.OK, entity = s"Request completed successfully"))
      context.parent ! Done
  }
}
