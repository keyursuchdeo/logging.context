package com.metro.logging.context.utils

import kamon.trace.Tracer
import org.slf4j.{Logger, LoggerFactory, MDC}

case class AppLogger(clazz: Class[_]) {
  private val logger: Logger = LoggerFactory.getLogger(clazz)

  def trace(msg: String): Unit = {
    setContext()
    logger.debug(msg)
  }

  def debug(msg: String): Unit = {
    setContext()
    logger.debug(msg)
  }

  def info(msg: String): Unit = {
    setContext()
    logger.info(msg)
  }

  def warn(msg: String): Unit = {
    setContext()
    logger.warn(msg)
  }

  def error(msg: String): Unit = {
    setContext()
    logger.error(msg)
  }

  private def setContext(): Unit = {
    MDC.put("country", Tracer.currentContext.tags("country"))
    MDC.put("traceId", Tracer.currentContext.token)
    MDC.put("traceName", Tracer.currentContext.name)
  }
}
