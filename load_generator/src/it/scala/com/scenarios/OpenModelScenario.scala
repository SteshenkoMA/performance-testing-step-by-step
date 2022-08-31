package com.scenarios

import com.chains.OrderCreationChain
import com.util.Configurator
import com.util.scenario.{ScenarioCreator, ScenarioInjector}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class OpenModelScenario extends Simulation {

  private val httpConf: HttpProtocolBuilder = http
    .baseUrl(Configurator.url)

  private val orderCreation_Scenario: ScenarioBuilder = ScenarioCreator.createOpenModelScenario("OrderCreation_Scenario_OpenModel", OrderCreationChain.execute)

  private val asserts = Seq(
    global.responseTime.percentile3.lte(5000)
  )

  setUp(
    orderCreation_Scenario.inject(ScenarioInjector.injectOpenModel)
  )
    .assertions(asserts)
    .protocols(httpConf)

}
