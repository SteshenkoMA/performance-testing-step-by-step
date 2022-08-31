package com.util.scenario

import com.util.Configurator
import com.util.Configurator.testDurationMilliseconds
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

import scala.concurrent.duration.{DurationInt}

object ScenarioCreator {

  def createOpenModelScenario(name: String, chain: ChainBuilder): ScenarioBuilder = {
    scenario(name)
      .exec(chain)
  }

  def createClosedModelScenario(name: String, chain: ChainBuilder): ScenarioBuilder = {
    val start = System.currentTimeMillis

    scenario(name)
      .asLongAs(session => (System.currentTimeMillis - start) < testDurationMilliseconds) {
        pace(Configurator.paceMilliseconds milliseconds)
          .exec(chain)
      }
  }

}
