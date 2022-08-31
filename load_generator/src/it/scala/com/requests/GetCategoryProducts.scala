package com.requests

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object GetCategoryProducts {

  /*
  Getting products of a category and saving a list of products
   */
  def getCategoryProducts(requestName: String): ChainBuilder = exec(
    getCategoryProductsHttpRqBuilder(requestName)
      .check(
        status is 200,
        jsonPath("$..id").findAll.saveAs("products"),
      )
  )

  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def getCategoryProductsHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .get("/eshop/control/category/${categories.random()}/products")
      .headers(headers_general)

}
