package com.requests

import com.util.Randomizer
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object SubmitPurchase {

  def submitPurchase(requestName: String): ChainBuilder = exec(
    setSessionVariables()
  ).exec(
    submitPurchaseHttpRqBuilder(requestName)
      .check(
        status is 200
      )
  )

  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def submitPurchaseHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .post("/eshop/control/checkout/purchase/submit")
      .body(ElFileBody("requests/json/submitPurchase.json")).asJson
      .headers(headers_general)

  private def setSessionVariables(): ChainBuilder = exec(
    session =>
      session
        .set("name", Randomizer.getName())
        .set("email", Randomizer.getEmail())
        .set("phone", Randomizer.getInt(9000000, 9999999))
        .set("address", Randomizer.getAddress())
        .set("ccNumber", Randomizer.getInt(1000000, 9999999))
  )

}
