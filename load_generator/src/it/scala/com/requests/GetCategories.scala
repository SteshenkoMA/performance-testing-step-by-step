package com.requests

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object GetCategories {
  /*
  Getting a list of product categories and checking the correctness of the data from the response.
  Saving a list of categories.
  */
  def getCategories(requestName: String): ChainBuilder = exec(
    getCategoriesHttpRqBuilder(requestName)
      .check(
        status is 200,
        jsonPath("$.categories").exists,
        jsonPath("$.categories").notNull,
        jsonPath("$.categories[*]").count.is(6),
        jsonPath("$..id").findAll.saveAs("categories")
      )
  )
  /*
  .exec(
    session => {
       println(session("categories"))
      session
    })
    */
  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def getCategoriesHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .get("/eshop/control/category/all")
      .headers(headers_general)


}
