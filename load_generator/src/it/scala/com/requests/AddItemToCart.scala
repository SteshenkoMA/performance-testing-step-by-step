package com.requests

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object AddItemToCart {

  /*
  Adding a randomly selected product to the cart
   */
  def addItemToCart(requestName: String): ChainBuilder = exec(
    addItemToCartHttpRqBuilder(requestName)
      .check(
        status is 200
      )
  )

  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def addItemToCartHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .post("/eshop/control/cart/additem")
      .body(ElFileBody("requests/json/addItemToCart.json")).asJson
      .headers(headers_general)

}