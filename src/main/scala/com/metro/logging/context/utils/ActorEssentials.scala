package com.metro.logging.context.utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object ActorEssentials {
  implicit val actorSystem: ActorSystem        = ActorSystem("actor-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}

