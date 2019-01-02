package com.metro.logging.context.models

case class CountryKey(private val code: String) {
  require(code.length == 2, "Invalid Country. Country code should 2 letters long (ISO 3166-1 alpha-2 standard)")
  val country: String = code.toUpperCase()
}

