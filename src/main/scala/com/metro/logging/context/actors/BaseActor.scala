package com.metro.logging.context.actors

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}

abstract class BaseActor extends Actor with ActorLogging {
  protected def createAndWatchActor(function: Function1[HttpResponse => Unit, Props],
                                    arg: HttpResponse => Unit,
                                    name: String): ActorRef = {
    val actorRef = context.actorOf(function(arg), s"$name-${UUID.randomUUID()}")
    context.watch(actorRef)
    actorRef
  }

  protected def createAndWatchActor[A](function: Function2[A, HttpResponse => Unit, Props],
                                       arg0: A,
                                       arg1: HttpResponse => Unit,
                                       name: String): ActorRef = {
    val actorRef =
      context.actorOf(function(arg0, arg1), s"$name-${UUID.randomUUID()}")
    context.watch(actorRef)
    actorRef
  }

  protected def createActor(props: Props, name: String): ActorRef =
    context.actorOf(props, s"$name-${UUID.randomUUID()}")

  protected def terminateAndRespondWithError(terminatedActorRef: ActorRef,
                                             completerFunction: HttpResponse => Unit): Unit = {
    log.error(s"Child Actor $terminatedActorRef terminated")
    completerFunction(HttpResponse(entity = "Internal Server Error", status = StatusCodes.InternalServerError))
    done()
  }

  protected def done(): Unit = {
    log.debug("Stopping actor..." + self.path.name)
    context.stop(self)
  }
}

