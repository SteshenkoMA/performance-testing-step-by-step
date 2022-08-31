package com.requests

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object GetCartInfo {
  /*
  Getting information about the shopping cart and saving a list of cart selected products.
  Optionally, saving a collection of products IDs and their quantity.
   */
  def getCartInfo(requestName: String): ChainBuilder = exec(
    getCartInfoHttpRqBuilder(requestName)
      .check(
        status is 200,
        jsonPath("$.cartInfo.cartItems..id").ofType[Int].findAll.saveAs("productsInCart"),
        jmesPath("cartInfo.cartItems[*].[product.id,quantity]").saveAs("IdQuantity")
      )
  )

  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def getCartInfoHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .get("/eshop/control/cart/getInfo")
      .headers(headers_general)

}