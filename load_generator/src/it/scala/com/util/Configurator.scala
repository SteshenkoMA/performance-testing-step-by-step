package com.util

import com.typesafe.config.ConfigFactory
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object Configurator {

  val conf = ConfigFactory.load().withFallback(ConfigFactory.parseResources("application.conf"))

  val url: String = conf.getString("url")
  val rampUpDurationMinutes = new FiniteDuration(conf.getInt("ramp-up-duration-minutes"), TimeUnit.MINUTES)
  val steadyStateDurationMinutes = new FiniteDuration(conf.getInt("steady-state-duration-minutes"), TimeUnit.MINUTES)
  val usersRatePerSecond: Int = conf.getInt("users-rate-per-second")
  val concurrentUsersAmount: Int = conf.getInt("concurrent-users-amount")
  val paceMilliseconds: Int = conf.getInt("pace-milliseconds")

  val testDurationMilliseconds = rampUpDurationMinutes.toMillis + steadyStateDurationMinutes.toMillis

}
