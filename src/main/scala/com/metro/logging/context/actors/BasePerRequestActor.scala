package com.metro.logging.context.actors

import akka.http.scaladsl.model.HttpResponse
import com.metro.logging.context.actors.BasePerRequestActor.HandleRequest

object BasePerRequestActor {
  trait RequestHandler
  case class HandleRequest(requestHandler: RequestHandler)

  val name: String = this.getClass.getSimpleName
}

abstract class BasePerRequestActor(completerFunction: HttpResponse => Unit) extends BaseActor {
  override def receive: Receive = {
    case HandleRequest(requestHandler) =>
      context.become(handleRequest)
      self ! requestHandler
  }

  protected def handleRequest: Receive

}

