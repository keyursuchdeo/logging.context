package com.metro.logging.context.actors

import akka.actor.Props
import com.metro.logging.context.actors.DataAccessActor.{ProcessDARequest, RequestProcessed}
import com.metro.logging.context.utils.AppLogger

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

object DataAccessActor {
  case object ProcessDARequest
  case object RequestProcessed

  lazy val props: Props = Props[DataAccessActor]
  lazy val name: String = this.getClass.getSimpleName
}

class DataAccessActor extends BaseActor {
  private val logger: AppLogger = AppLogger(this.getClass)
  private implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  override def receive: Receive = {
    case ProcessDARequest => processDARequest()
  }

  private def processDARequest(): Unit = {
    logger.info("Received req to test logging context in DataAccessActor")
    Future.successful("success").map (_ => {
      logger.info("Received req to test logging context in DataAccessActor-Future callback")
      context.parent ! RequestProcessed
    })
  }
}
