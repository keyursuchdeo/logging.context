import Versions._
import sbt._

object Dependencies {
  object Akka {
    val akkaHttp            = "com.typesafe.akka"  %% "akka-http"               % akkaHttpV
    val akkaHttpSprayJson   = "com.typesafe.akka"  %% "akka-http-spray-json"    % akkaHttpSprayJsonV
    val akkaHttpCors        = "ch.megard"          %% "akka-http-cors"          % akkaHttpCorsV
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaV
  }

  object Logging {
    val akkaSlf4j              = "com.typesafe.akka"      %% "akka-slf4j"              % akkaSlf4jV
    val logbackClassic         = "ch.qos.logback"         % "logback-classic"          % logbackV
    val logstashLogbackEncoder = "net.logstash.logback"   % "logstash-logback-encoder" % logstashV
  }

  object Kamon {
    val kamonCore = "io.kamon" %% "kamon-core" % kamonCoreV
    val kamonScala = "io.kamon" %% "kamon-scala" % kamonScalaV
    val kamonAkka = "io.kamon" %% "kamon-akka-2.5" % kamonAkkaV % "runtime"
    val kamonAkkaHttp = "io.kamon" %% "kamon-akka-http" % kamonAkkaHttpV exclude("io.kamon", "kamon-akka-2.4_2.12")
    val kamonAutoweave = "io.kamon" %% "kamon-autoweave" % kamonAutoweaveV
  }

  val akka = Seq(
    Akka.akkaHttp,
    Akka.akkaHttpSprayJson,
    Akka.akkaHttpCors,
    Akka.akkaStream
  )

  val logging   = Seq(Logging.akkaSlf4j, Logging.logbackClassic, Logging.logstashLogbackEncoder)

  val kamon = Seq(Kamon.kamonCore, Kamon.kamonScala, Kamon.kamonAkka, Kamon. kamonAkkaHttp, Kamon.kamonAutoweave)

}
