package com.util.scenario

import com.util.Configurator
import io.gatling.core.Predef._
import io.gatling.core.controller.inject.closed.ClosedInjectionStep
import io.gatling.core.controller.inject.open.OpenInjectionStep

object ScenarioInjector {

  def injectOpenModel: Seq[OpenInjectionStep] = {
    Seq.apply(
      rampUsersPerSec(0) to (Configurator.usersRatePerSecond) during Configurator.rampUpDurationMinutes,
      constantUsersPerSec(Configurator.usersRatePerSecond) during Configurator.steadyStateDurationMinutes
    )
  }

  def injectClosedModel: Seq[ClosedInjectionStep] = {
    Seq.apply(
      rampConcurrentUsers(0).to(Configurator.concurrentUsersAmount).during(Configurator.rampUpDurationMinutes),
      constantConcurrentUsers(Configurator.concurrentUsersAmount).during(Configurator.steadyStateDurationMinutes)
    )
  }

}
