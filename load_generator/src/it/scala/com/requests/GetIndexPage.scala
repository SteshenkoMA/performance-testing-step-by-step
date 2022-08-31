package com.requests

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object GetIndexPage {

  /*
  Accessing the main page of the site and getting the session ID (sid)
   */
  def getIndexPage(requestName: String): ChainBuilder = exec(
    getIndexPageHttpRqBuilder(requestName)
      .check(
        status is 200
      )

  )
  /*
    .exec(
    session => {
      println(session("sid").as[String])
      session
    })
   */

  val headers_general = Map(
    HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson,
    HttpHeaderNames.Accept -> HttpHeaderValues.ApplicationJson
  )

  private def getIndexPageHttpRqBuilder(requestName: String): HttpRequestBuilder =
    http(requestName)
      .get("/eshop")
      .headers(headers_general)

}
