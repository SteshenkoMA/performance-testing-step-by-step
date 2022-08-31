package com.requests

import com.util.Randomizer
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object UpdateQuantity {

  /*
  Increasing the number of randomly selected cart product by a random number.
  Checking the response to the fact that the quantity of the cart product has changed to this selected number.
   */
  def updateQuantity(requestName: String): ChainBuilder = exec(
    setSessionVariables()
  ).exec(
    updateQuantityHttpRqBuilder(requestName)
      .check(
        status is 200,
        jsonPath(session => "$.cartInfo.cartItems[?(@.product.id=="
          + session("selectedProductId").as[String]
          + ")].quantity").is(session => session("quantity").as[String])
      )
  )

  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def updateQuantityHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .post("/eshop/control/cart/updatequantity")
      .body(ElFileBody("requests/json/updateQuantity.json")).asJson
      .headers(headers_general)

  private def setSessionVariables(): ChainBuilder = exec(
    session =>
      session
        .set("quantity", Randomizer.getInt(2, 10))
        .set("selectedProductId", Randomizer.getIntFromSeq(session("productsInCart").as[Seq[Int]]))
  )
  /*
    .exec(
    session => {
       println("selectedProductId: " + session("selectedProductId").as[String])
       println("quantity: " + session("quantity").as[String])
      session
    })
  */
}
